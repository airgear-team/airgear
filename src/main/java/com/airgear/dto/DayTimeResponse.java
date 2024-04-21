package com.airgear.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class DayTimeResponse {

    LocalTime timeFrom;

    LocalTime timeTo;

    Boolean isFree;

}
