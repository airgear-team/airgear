package com.airgear.service;

import com.airgear.dto.ComplaintDto;

public interface ComplaintService {

    ComplaintDto save(String userName, Long goodsId, ComplaintDto complaint);
}
