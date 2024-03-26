package com.airgear.service.impl;

import com.airgear.dto.ComplaintDto;
import com.airgear.mapper.ComplaintMapper;
import com.airgear.model.Complaint;
import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import com.airgear.repository.ComplaintRepository;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.ComplaintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service(value = "complaintService")
@AllArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final ComplaintMapper complaintMapper;

    @Override
    @Transactional
    public ComplaintDto save(String userName, Long goodsId, ComplaintDto complaintDTO) {
        Complaint newComplaint = complaintMapper.toModel(complaintDTO);
        User user = userRepository.findByUsername(userName);
        Goods goods = goodsRepository.getReferenceById(goodsId);
        newComplaint.setUser(user);
        newComplaint.setGoods(goods);
        newComplaint.setCreatedAt(OffsetDateTime.now());
        return complaintMapper.toDto(complaintRepository.save(newComplaint));
    }
}
