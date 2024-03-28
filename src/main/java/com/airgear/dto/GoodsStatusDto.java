package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodsStatusDto {
    private Long id;
    private String name;
}
