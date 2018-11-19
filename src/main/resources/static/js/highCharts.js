function createStackColumnChart(container, titleX, titleY, categoriesArray, series ) {
    Highcharts.chart(container, { // 'containerBloodSugar'
        chart: {
            type: 'column'
        },
        title: {
            text: titleX //'Stacked column chart'
        },
        xAxis: {
            categories: categoriesArray // e.g.: ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']
        },
        yAxis: {
            min: 0,
            title: {
                text:  titleY //'Total fruit consumption'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        tooltip: {
            headerFormat: '<b>{point.x}</b><br/>',
            pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                }
            }
        },
        series: series/* [{
            name: 'John',
            data: [5, 3, 4, 7, 2]
        }, {
            name: 'Jane',
            data: [2, 2, 3, 2, 1]
        }, {
            name: 'Joe',
            data: [3, 4, 4, 2, 5]
        }]*/
    });

}


// ----------------------------------------------------spline chart----------------------------------------------------

function createSplineChart(container, title,  titleX, titleY1, titleY2, titleY3,  categoriesArray, unitY1,unitY2, unitY3, seriesData1, seriesData2, seriesData3) {
    Highcharts.chart('containerComplexData', {
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: title //'Average Monthly Weather Data for Tokyo'
        },
        subtitle: {
            text: 'Source: WorldClimate.com'
        },
        xAxis: [{
            categories: categoriesArray, /*['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],*/
            crosshair: true
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}' + unitY1,
                style: {
                    color: Highcharts.getOptions().colors[2]
                }
            },
            title: {
                text:  titleY1 , //'Temperature',
                style: {
                    color: Highcharts.getOptions().colors[2]
                }
            },
            opposite: true

        }, { // Secondary yAxis
            gridLineWidth: 0,
            title: {
                text: titleY2,//'Rainfall',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value} ' + unitY2,
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            }

        }, { // Tertiary yAxis
            gridLineWidth: 0,
            title: {
                text:  titleY3, //'Sea-Level Pressure',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            labels: {
                format: '{value}' + unitY3,
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 80,
            verticalAlign: 'top',
            y: 55,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || 'rgba(255,255,255,0.25)'
        },
        series: [{
            name: titleY1, //'Rainfall',
            type: 'column',
            yAxis: 1,
            data:  seriesData1, //[49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
            tooltip: {
                valueSuffix: " " +unitY1//' mm'
            }

        }, {
            name: titleY2, // 'Sea-Level Pressure',
            type: 'spline',
            yAxis: 2,
            data: seriesData2, // [1016, 1016, 1015.9, 1015.5, 1012.3, 1009.5, 1009.6, 1010.2, 1013.1, 1016.9, 1018.2, 1016.7],
            marker: {
                enabled: false
            },
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix:" "+ unitY2// ' mb'
            }

        }, {
            name: titleY3 , // 'Temperature',
            type: 'spline',
            data:seriesData3,// [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
            tooltip: {
                valueSuffix: " " + unitY3// ' °C'
            }
        }]
    });

}




/*
The purpose of this demo is to demonstrate how multiple charts on the same page
can be linked through DOM and Highcharts events and API methods. It takes a
standard Highcharts config with a small variation for each data set, and a
mouse/touch event handler to bind the charts together.
*/



/**
 * In order to synchronize tooltips and crosshairs, override the
 * built-in events with handlers defined on the parent element.
 */
['mousemove', 'touchmove', 'touchstart'].forEach(function (eventType) {
    document.getElementById('containerComplex3').addEventListener(
        eventType,
        function (e) {
            var chart,
                point,
                i,
                event;

            for (i = 0; i < Highcharts.charts.length; i = i + 1) {
                chart = Highcharts.charts[i];
                // Find coordinates within the chart
                event = chart.pointer.normalize(e);
                // Get the hovered point
                point = chart.series[0].searchPoint(event, true);

                if (point) {
                    point.highlight(e);
                }
            }
        }
    );
});

/**
 * Override the reset function, we don't need to hide the tooltips and
 * crosshairs.
 */
Highcharts.Pointer.prototype.reset = function () {
    return undefined;
};

/**
 * Highlight a point by showing tooltip, setting hover state and draw crosshair
 */
Highcharts.Point.prototype.highlight = function (event) {
    event = this.series.chart.pointer.normalize(event);
    this.onMouseOver(); // Show the hover marker
    this.series.chart.tooltip.refresh(this); // Show the tooltip
    this.series.chart.xAxis[0].drawCrosshair(event, this); // Show the crosshair
};

/**
 * Synchronize zooming through the setExtremes event handler.
 */
function syncExtremes(e) {
    var thisChart = this.chart;

    if (e.trigger !== 'syncExtremes') { // Prevent feedback loop
        Highcharts.each(Highcharts.charts, function (chart) {
            if (chart !== thisChart) {
                if (chart.xAxis[0].setExtremes) { // It is null while updating
                    chart.xAxis[0].setExtremes(
                        e.min,
                        e.max,
                        undefined,
                        false,
                        { trigger: 'syncExtremes' }
                    );
                }
            }
        });
    }
}



// ------------------------------------------------LINECHART------------------------------------------------------------
function createLineChart(container, titleX ,titleY,  categories, seriries) {
    var chart = Highcharts.chart(container, {
        chart: {
            type: 'line'
        },
        title: {
            text: titleX //'Stacked column chart'
        },
        yAxis:{
            title: {
                text:  titleY //'Total fruit consumption'
            },
        },
        xAxis: {
            categories: categories
        },
        plotOptions: {
            series: {
                allowPointSelect: true
            }
        },
        series:seriries
    });
}


// HEARTRATE MINMAX AVE


function createRangeChart (container,  ranges, avarages, title,  unitY, measurement) {


  /*  var ranges = [
            [1246406400000, 14.3, 27.7],
            [1246492800000, 14.5, 27.8],
            [1246579200000, 15.5, 29.6],
            [1246665600000, 16.7, 30.7],

        ],
        averages = [
            [1246406400000, 21.5],
            [1246492800000, 22.1],
            [1246579200000, 23],
            [1246665600000, 23.8],
        ];
*/

    Highcharts.chart(container, {

        title: {
            text: title // 'July temperatures'
        },

        xAxis: {
            type: 'datetime'
        },

        yAxis: {
            title: {
                text: null
            }
        },

        tooltip: {
            crosshairs: true,
            shared: true,
            valueSuffix:  unitY// '°C'
        },

        legend: {},

        series: [{
            name: measurement, //'Temperature',
            data: averages,
            zIndex: 1,
            marker: {
                fillColor: 'white',
                lineWidth: 2,
                lineColor: Highcharts.getOptions().colors[0]
            }
        }, {
            name: 'Range',
            data: ranges,
            type: 'arearange',
            lineWidth: 0,
            linkedTo: ':previous',
            color: Highcharts.getOptions().colors[0],
            fillOpacity: 0.3,
            zIndex: 0,
            marker: {
                enabled: false
            }
        }]
    });

}
//---------------------------------------------ColumnChart---------------------------------------------------------------
function createColumnChart(container,title, yAxisText,categories, seriries ) {

        var chart = Highcharts.chart(container, {
            chart: {
                type: 'column'
            },
            title: {
                text: title //'Stacked column chart'
            },
            yAxis:{
                title: {
                    text:  yAxisText //'Total fruit consumption'
                }
            },
            xAxis: {
                categories: categories
            },
            plotOptions: {
                series: {
                    allowPointSelect: true
                }
            },
            series:seriries
        });



}