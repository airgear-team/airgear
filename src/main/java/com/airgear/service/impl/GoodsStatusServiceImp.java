package com.airgear.service.impl;

import com.airgear.exception.GoodsExceptions;
import com.airgear.model.goods.GoodsStatus;
import com.airgear.repository.GoodsStatusRepository;
import com.airgear.service.GoodsStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service(value = "goodsStatusService")
@AllArgsConstructor
public class GoodsStatusServiceImp implements GoodsStatusService {

    private final GoodsStatusRepository goodsStatusRepository;

    @Override
    public GoodsStatus getGoodsById(Long id) {
        return goodsStatusRepository.findById(id).orElseThrow(() -> GoodsExceptions.goodsDataNotFound("GoodsStatus", id.toString()));
    }
}
