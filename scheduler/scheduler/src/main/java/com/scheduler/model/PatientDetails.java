package com.scheduler.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(value = "SchedulerDetails")
public class PatientDetails {

    @Id
    private Integer schedulerID;
    private Scheduler scheduler;

}
