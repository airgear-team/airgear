package com.airgear.service;

import com.airgear.model.Complaint;
import com.airgear.model.ComplaintCategory;

public interface ComplaintService {
    ComplaintCategory getComplaintCategoryById(Long id);

    void save(Complaint complaint);
}
