package com.airgear.dto;

import com.airgear.model.Complaint;
import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class ComplaintDTO {
    private User user;
    private ComplaintCategoryDTO complaintCategoryDTO;
    private Goods goods;
    private String description;
    private OffsetDateTime createdAt;

    public Complaint getComplaintFromDto() {
        return Complaint.builder()
                .user(user)
                .complaintCategory(complaintCategoryDTO.getComplaintCategoryFromDto())
                .goods(goods)
                .description(description)
                .createdAt(createdAt).
                build();
    }
}


