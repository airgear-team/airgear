package com.airgear.dto;

import com.airgear.model.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class GoodsResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Location location;
    private Long userId;
}
