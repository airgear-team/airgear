package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//public record RegionResponseDTO(Long id,
//                                String region) {
//
//    public static RegionResponseDTO fromRegion(Region region) {
//        return new RegionResponseDTO(
//                region.getId(),
//                region.getRegion()
//        );
//    }
//}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponseDTO {
    private Long id;
    private String region;
}
