package com.airgear.mapper;

import com.airgear.dto.ComplaintDto;
import com.airgear.model.Complaint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, ComplaintCategoryMapper.class, GoodsMapper.class})
public interface ComplaintMapper {
    ComplaintDto toDto(Complaint complaint);

    Complaint toModel(ComplaintDto dto);
}
