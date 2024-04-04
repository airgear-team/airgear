package com.airgear.dto;

import com.airgear.model.Roles;
import lombok.AllArgsConstructor;
import com.airgear.model.UserStatus;
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
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private Set<Roles> roles;
    private Set<GoodsDto> goods;
    private OffsetDateTime createdAt;
    private OffsetDateTime deleteAt;
    private UserStatus status;
    private Set<UserReviewDto> userReviews;
}