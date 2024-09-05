package com.airgear.dto;

import com.airgear.model.Role;
import com.airgear.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetResponse {

    private Long id;
    private String email;
    private String phone;
    private String name;
    private Set<Role> roles;
    private Set<GoodsGetResponse> goods;
    private OffsetDateTime createdAt;
    private OffsetDateTime deleteAt;
    private UserStatus status;
    private Set<UserReviewGetResponse> userReviews;
    private String userDescription;
}
