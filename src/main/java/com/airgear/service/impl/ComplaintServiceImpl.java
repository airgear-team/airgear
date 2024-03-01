package com.airgear.service.impl;

import com.airgear.model.Complaint;
import com.airgear.model.ComplaintCategory;
import com.airgear.repository.ComplaintCategoryRepository;
import com.airgear.repository.ComplaintRepository;
import com.airgear.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "complaintService")
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private ComplaintCategoryRepository complaintCategoryRepository;

    @Override
    public ComplaintCategory getComplaintCategoryById(Long id) {
        return complaintCategoryRepository.getReferenceById(id);
    }

    @Override
    public void save(Complaint complaint) {
        complaintRepository.save(complaint);
    }
}
