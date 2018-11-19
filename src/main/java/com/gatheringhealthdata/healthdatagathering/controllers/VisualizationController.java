package com.gatheringhealthdata.healthdatagathering.controllers;

import com.gatheringhealthdata.healthdatagathering.entities.HealthDataAtomic;
import com.gatheringhealthdata.healthdatagathering.services.AtomicService;
import com.gatheringhealthdata.healthdatagathering.services.ComplexService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
@RequestMapping("data")
public class VisualizationController {

    Calendar calendar = Calendar.getInstance();
    private AtomicService atomicService;
    private ComplexService complexService;

    @Autowired
    public void setAtomicAndComplexServices(AtomicService atomicService, ComplexService complexService) {
        this.atomicService = atomicService;
        this.complexService = complexService;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard(Map<String, Object> model) {
        List<HealthDataAtomic> allFloors = atomicService.findByName("floorsClimbed");
        List<String> dates = allFloors.stream().map(data -> {
            DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
            String date = dateFormat.format(data.getStartTime());
            return date;
        }).collect(Collectors.toList());
        List<Integer> sleepMinutes = atomicService.findByName("sleepTime").stream().map(data -> {
            Date date = new Date(1546297200000L);
            if (data.getStartTime().getTime() != 0L && data.getStartTime().compareTo(date) < 0) {
                long diff = data.getEndTime().getTime() - data.getStartTime().getTime();
                long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                return (int) diffMinutes;
            }
            return 0;
        }).filter(time -> time != 0).collect(Collectors.toList());

        Map<String, List<Integer>> sleepMinutesMap = new HashMap<>();
        sleepMinutesMap.put("sleep", sleepMinutes);
        String sleepMinutesAsJSON = new Gson().toJson(sleepMinutesMap);
        Map<String, List<Float>> stepsAndFloors = getDataSeries(new String[]{"steps", "floorsClimbed"});
        Map<String, List<Float>> caffeineIntakeAndBloodGlucose = getDataSeries(new String[]{"caffeineIntake", "bloodGlucose"});
        Map<String, List<Float>> caffeineIntakeAndHeartRate = getDataSeries(new String[]{"caffeineIntake", "heartRate"});
        System.out.println(Arrays.toString(dates.toArray()));
        String datesAsJSON = new Gson().toJson(dates);
        String stepsAndFloorsAsJSON = new Gson().toJson(stepsAndFloors);
        String caffeineIntakeAndHeartRateAsJSON = new Gson().toJson(caffeineIntakeAndHeartRate);
        String caffeineIntakeAndBloodGlucoseAsJson = new Gson().toJson(caffeineIntakeAndBloodGlucose);
        model.put("sleep", sleepMinutesAsJSON);
        model.put("dates", datesAsJSON);
        model.put("stepsAndFloors", stepsAndFloorsAsJSON);
        model.put("caffeineIntakeAndBloodGlucose", caffeineIntakeAndBloodGlucoseAsJson);
        model.put("caffeineIntakeAndHeartRate", caffeineIntakeAndHeartRateAsJSON);
        return "dashboard";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String checkUpUser(Map<String, Object> model) {
        model.put(" ", " ");
        return "user";
    }

    public Map<String, List<Float>> getDataSeries(String[] names) {
        Map<String, List<Float>> series = new HashMap<>();
        for (String name : names) {
            List<HealthDataAtomic> dataFromDb = atomicService.findByName(name);
            List<Float> seriesArray = dataFromDb.stream().map(data -> data.getFloatValue()).collect(Collectors.toList());
            series.put(name, seriesArray);
        }
        return series;
    }

}
