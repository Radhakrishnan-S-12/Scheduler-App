package com.scheduler.dto;

import com.scheduler.constants.FrequencyEnums;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SchedulerDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime time;
    private LocalDateTime duration;
    private boolean repeat;
    private FrequencyEnums frequencyEnums;


}
