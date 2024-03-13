package com.airgear.mapper;

import com.airgear.dto.ComplaintCategoryDto;
import com.airgear.model.ComplaintCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComplaintCategoryMapper {
    ComplaintCategoryDto toDto(ComplaintCategory complaintCategory);

    ComplaintCategory toComplaintCategory(ComplaintCategoryDto dto);
}
