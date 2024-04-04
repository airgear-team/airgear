package com.airgear.service.impl;

import com.airgear.model.goods.GoodsStatus;
import com.airgear.repository.GoodsStatusRepository;
import com.airgear.service.GoodsStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "goodsStatusService")
@AllArgsConstructor
public class GoodsStatusServiceImp implements GoodsStatusService {

    private final GoodsStatusRepository goodsStatusRepository;

    @Override
    public GoodsStatus getGoodsById(Long id) {
        return goodsStatusRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("GoodsStatus not found"));
    }
}
