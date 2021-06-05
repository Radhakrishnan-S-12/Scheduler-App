package com.scheduler.model;


import com.scheduler.constants.FrequencyEnums;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Scheduler {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean repeat;
    private FrequencyEnums frequencyEnums;
    private LocalDateTime schedulerTime;
}
