package com.airgear.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private Set<RoleDto> roles;
    private Set<GoodsDto> goods;
    private OffsetDateTime createdAt;
    private OffsetDateTime deleteAt;
    private AccountStatusDto accountStatus;
    private Set<UserReviewDto> userReviews;
}