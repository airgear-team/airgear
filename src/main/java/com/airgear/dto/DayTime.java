package com.airgear.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class DayTime {
    LocalTime timeFrom;
    LocalTime timeTo;
    Boolean free;
}
