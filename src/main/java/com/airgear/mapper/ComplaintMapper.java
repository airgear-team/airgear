package com.airgear.mapper;

import com.airgear.dto.ComplaintCreateRequest;
import com.airgear.dto.ComplaintCreateResponse;
import com.airgear.model.Complaint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, ComplaintCategoryMapper.class, GoodsMapper.class})
public interface ComplaintMapper {
    ComplaintCreateResponse toDto(Complaint complaint);

    Complaint toModel(ComplaintCreateRequest dto);
}
