package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

/**
 * UserDto class. Fields are similar to User entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
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
    private Set<RoleDto> roles;
    private Set<GoodsDto> goods;
    private OffsetDateTime createdAt;
    private OffsetDateTime deleteAt;
    private AccountStatusDto accountStatus;
    private Set<UserReviewDto> userReviews;
}