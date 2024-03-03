package com.airgear.service;

import com.airgear.dto.ComplaintDTO;
import com.airgear.model.Complaint;

public interface ComplaintService {

    Complaint save(String userName, Long goodsId, ComplaintDTO complaint);
}
