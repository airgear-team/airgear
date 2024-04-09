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
public class UserDto {

    private Long id;
    private String email;
    private String phone;
    private String name;
    private Set<Role> roles;
    private Set<GoodsDto> goods;
    private OffsetDateTime createdAt;
    private OffsetDateTime deleteAt;
    private UserStatus status;
    private Set<UserReviewDto> userReviews;

}
