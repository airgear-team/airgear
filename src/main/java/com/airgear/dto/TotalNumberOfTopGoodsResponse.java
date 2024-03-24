package com.airgear.dto;

import lombok.Data;

@Data
public class TotalNumberOfTopGoodsResponse {

    private Long totalNumberOfTopGoods;

    public TotalNumberOfTopGoodsResponse(Long totalNumberOfTopGoods) {
        this.totalNumberOfTopGoods = totalNumberOfTopGoods;
    }
}
