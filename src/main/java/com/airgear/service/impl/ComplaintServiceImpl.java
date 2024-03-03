package com.airgear.service.impl;

import com.airgear.dto.ComplaintDTO;
import com.airgear.model.Complaint;
import com.airgear.model.ComplaintCategory;
import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import com.airgear.repository.ComplaintCategoryRepository;
import com.airgear.repository.ComplaintRepository;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service(value = "complaintService")
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintCategoryRepository complaintCategoryRepository;
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository, ComplaintCategoryRepository complaintCategoryRepository, UserRepository userRepository, GoodsRepository goodsRepository) {
        this.complaintRepository = complaintRepository;
        this.complaintCategoryRepository = complaintCategoryRepository;
        this.userRepository = userRepository;
        this.goodsRepository = goodsRepository;
    }

    @Override
    @Transactional
    public Complaint save(String userName, Long goodsId, ComplaintDTO complaintDTO) {
        Complaint newComplaint = complaintDTO.getComplaintFromDto();
        User user = userRepository.findByUsername(userName);
        Goods goods = goodsRepository.getReferenceById(goodsId);
        ComplaintCategory complaintCategory = complaintCategoryRepository.findByName(complaintDTO.getComplaintCategoryDTO().getName());
        newComplaint.setComplaintCategory(complaintCategory);
        newComplaint.setUser(user);
        newComplaint.setGoods(goods);
        newComplaint.setCreatedAt(OffsetDateTime.now());
        return complaintRepository.save(newComplaint);
    }
}
