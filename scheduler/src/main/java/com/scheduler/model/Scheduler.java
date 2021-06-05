package com.scheduler.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.scheduler.constants.FrequencyEnums;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Scheduler {

	@NonNull
	private LocalDate startDate;
	@NonNull
	private LocalDate endDate;
	private boolean repeat;
	private boolean activeStatus = true;
	private FrequencyEnums frequencyEnums;
	@NonNull
	private LocalTime schedulerTime;
	@NonNull
	private String duration;
}
