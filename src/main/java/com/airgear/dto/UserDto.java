package com.airgear.dto;

import com.airgear.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private Set<RoleDto> roles;
    private Set<GoodsDto> goods;
    private OffsetDateTime createdAt;
    private OffsetDateTime deleteAt;
    private AccountStatusDto accountStatus;
    private Set<UserReviewDto> userReviews;

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .phone(phone)
                .name(name)
                .roles(roles == null ? null : RoleDto.toRoles(roles))
                .goods(goods == null ? null : GoodsDto.toGoodsSet(goods))
                .createdAt(createdAt)
                .deleteAt(deleteAt)
                .accountStatus(accountStatus == null ? null : accountStatus.toAccountStatus())
                .userReviews(userReviews == null ? null : UserReviewDto.toUserReviews(userReviews))
                .build();
    }

    public List<User> toUsers(List<UserDto> users) {
        List<User> result = new ArrayList<>();
        users.forEach(userDto -> result.add(userDto.toUser()));
        return result;
    }

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .name(user.getName())
                .roles(RoleDto.fromRoles(user.getRoles()))
                .goods(GoodsDto.fromGoodsSet(user.getGoods()))
                .createdAt(user.getCreatedAt())
                .deleteAt(user.getDeleteAt())
                .accountStatus(AccountStatusDto.fromAccountStatus(user.getAccountStatus()))
                .userReviews(UserReviewDto.fromUserReviews(user.getUserReviews()))
                .build();
    }

    public static List<UserDto> fromUsers(List<User> users) {
        List<UserDto> result = new ArrayList<>();
        users.forEach(user -> result.add(UserDto.fromUser(user)));
        return result;
    }


}