package com.airgear.dto;

import com.airgear.model.Roles;
import lombok.Builder;
import lombok.Data;

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
    private AccountStatusDto accountStatus;
    private Set<UserReviewDto> userReviews;
}