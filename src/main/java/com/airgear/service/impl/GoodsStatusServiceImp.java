package com.airgear.service.impl;

import com.airgear.model.goods.GoodsStatus;
import com.airgear.repository.GoodsStatusRepository;
import com.airgear.service.GoodsStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "goodsStatusService")
public class GoodsStatusServiceImp implements GoodsStatusService {
    @Autowired
    private GoodsStatusRepository goodsStatusRepository;
    @Override
    public GoodsStatus getGoodsById(Long id) {
        return goodsStatusRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("GoodsStatus not found"));
    }
}
