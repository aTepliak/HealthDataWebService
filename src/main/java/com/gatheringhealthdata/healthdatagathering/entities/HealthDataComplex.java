package com.gatheringhealthdata.healthdatagathering.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "complex",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "start_time"})})
public class HealthDataComplex {
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

    @ManyToMany
    @JoinTable(name = "atomic_complex", joinColumns = {@JoinColumn(name = "complexId",  nullable = true, updatable = true)},
            inverseJoinColumns = {@JoinColumn(name = "atomicId",  nullable = true)})
    @JsonBackReference
    private List<HealthDataAtomic> atomicValues = new ArrayList<>();


    public HealthDataComplex() {
    }

    public HealthDataComplex(String name, Date startTime, Date endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<HealthDataComplex> complexElementsInside = new HashSet<HealthDataComplex>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "complexElementsInside")
    private Set<HealthDataComplex> complexElementOf = new HashSet<HealthDataComplex>();

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

    public List<HealthDataAtomic> getAtomicValues() {
        return atomicValues;
    }

    public void setAtomicValues(List<HealthDataAtomic> atomicElements) {
        this.atomicValues = atomicValues;
    }

    public Set<HealthDataComplex> getComplexElementsInside() {
        return complexElementsInside;
    }

    public void setComplexElementsInside(Set<HealthDataComplex> complexElementsInside) {
        this.complexElementsInside = complexElementsInside;
    }

    public Set<HealthDataComplex> getComplexElementOf() {
        return complexElementOf;
    }

    public void setComplexElementOf(Set<HealthDataComplex> complexElementOf) {
        this.complexElementOf = complexElementOf;
    }
}
