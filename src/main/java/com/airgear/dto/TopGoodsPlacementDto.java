package com.airgear.dto;

import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Builder
public class TopGoodsPlacementDto {
    private Long id;
    private Long userId;
    private GoodsDto goods;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;

}
