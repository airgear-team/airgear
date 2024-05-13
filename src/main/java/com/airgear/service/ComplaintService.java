package com.airgear.service;

import com.airgear.dto.ComplaintCreateRequest;
import com.airgear.dto.ComplaintCreateResponse;

public interface ComplaintService {

    ComplaintCreateResponse save(String userName, Long goodsId, ComplaintCreateRequest complaint);
}
