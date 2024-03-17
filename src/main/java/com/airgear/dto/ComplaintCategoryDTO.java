package com.airgear.dto;

import com.airgear.model.ComplaintCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class ComplaintCategoryDTO {

    private String name;

    public ComplaintCategory getComplaintCategoryFromDto() {
        return ComplaintCategory.builder()
                .name(name)
                .build();
    }


}
