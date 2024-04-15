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
    Long id;
    String name;
    String description;
    Double price;
    Location location;
    Long userId;
}
