package com.airgear.utils;

import com.airgear.dto.ComplaintCategoryDTO;
import com.airgear.dto.ComplaintDTO;
import com.airgear.dto.UserDto;
import com.airgear.model.Complaint;
import com.airgear.model.ComplaintCategory;
import com.airgear.model.User;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public static ComplaintCategoryDTO getDtoFromComplaintCategory(ComplaintCategory complaintCategory) {
        return ComplaintCategoryDTO.builder()
                .name(complaintCategory.getName())
                .build();
    }

    public static ComplaintDTO getDtoFromComplaint(Complaint complaint) {
        return ComplaintDTO.builder()
                .user(complaint.getUser())
                .complaintCategoryDTO(Converter.getDtoFromComplaintCategory(complaint.getComplaintCategory()))
                .goods(complaint.getGoods())
                .description(complaint.getDescription())
                .createdAt(complaint.getCreatedAt())
                .build();
    }
}
