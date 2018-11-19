package com.gatheringhealthdata.healthdatagathering.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "atomic",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "start_time"})})
public class HealthDataAtomic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(name = "start_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private String stringValue;
    private float floatValue;


    @ManyToMany(mappedBy = "atomicValues", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<HealthDataComplex> complexElements = new ArrayList<>();

    public HealthDataAtomic() {
    }

    public HealthDataAtomic(String name, Date startTime, Date endTime, String stringValue, float floatValue) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stringValue = stringValue;
        this.floatValue = floatValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    public List<HealthDataComplex> getComplexelements() {
        return complexElements;
    }

    public void setComplexelements(List<HealthDataComplex> complexElements) {
        this.complexElements = complexElements;
    }
}
