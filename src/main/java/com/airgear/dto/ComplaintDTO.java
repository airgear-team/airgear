package com.airgear.dto;

import com.airgear.model.Complaint;
import com.airgear.model.ComplaintCategory;
import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
public class ComplaintDTO {
    private User user;
    private ComplaintCategory complaintCategory;
    private Goods goods;
    private String description;
    private OffsetDateTime createdAt;

    public Complaint getComplaintFromDto() {
        return Complaint.builder()
                .description(description)
                .createdAt(createdAt).
                build();
    }
}
