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
    private LocalDate date;
    private Boolean free;
    private Long goodsId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<DayTime> listTime;
}
