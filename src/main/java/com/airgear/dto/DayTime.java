package com.airgear.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class DayTime {
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private Boolean free;
}
