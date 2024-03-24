package com.airgear.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDay {
    LocalDate date;
    Boolean free;
    Long goodsId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Set<DayTime> listTime;
}
