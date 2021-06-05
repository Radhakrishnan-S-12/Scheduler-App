package com.scheduler.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.scheduler.constants.FrequencyEnums;

import lombok.Data;

@Data
public class SchedulerDTO {

	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime time;
	private String duration;
	private boolean repeat;
	private FrequencyEnums frequencyEnums;

}
