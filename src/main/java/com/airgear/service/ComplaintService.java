package com.airgear.service;

import com.airgear.dto.ComplaintDto;
import com.airgear.model.Complaint;

public interface ComplaintService {

    Complaint save(String userName, Long goodsId, ComplaintDto complaint);
}
