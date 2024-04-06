package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDTO {
    private Long id;
    private String settlement;
    private Long regionId;
}
