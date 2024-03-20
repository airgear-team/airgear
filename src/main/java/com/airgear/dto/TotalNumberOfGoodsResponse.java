package com.airgear.dto;

import lombok.Data;

@Data
public class TotalNumberOfGoodsResponse {

    private Long totalNumberOfGoods;

    public TotalNumberOfGoodsResponse(Long totalNumberOfGoods) {
        this.totalNumberOfGoods = totalNumberOfGoods;
    }
}
