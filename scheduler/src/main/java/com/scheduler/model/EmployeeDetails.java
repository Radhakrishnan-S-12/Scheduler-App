package com.scheduler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Document(value = "SchedulerDetails")
public class EmployeeDetails {

	@Id
	private String employeeEmailID;
	@NonNull
	private Scheduler scheduler;

}
