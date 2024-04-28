package com.airgear.mapper;

import com.airgear.dto.ComplaintCategoryRequest;
import com.airgear.dto.ComplaintCategoryResponse;
import com.airgear.model.ComplaintCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComplaintCategoryMapper {
    ComplaintCategoryResponse toDto(ComplaintCategory complaintCategory);

    ComplaintCategory toModel(ComplaintCategoryRequest dto);
}
