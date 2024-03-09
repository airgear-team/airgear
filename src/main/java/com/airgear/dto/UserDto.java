package com.airgear.dto;

import com.airgear.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String email;
    private String phone;
    private String name;
    private List<RoleDto> roles;
    private List<GoodsDto> goods;
    private OffsetDateTime createdAt;
    private OffsetDateTime deleteAt;
    private AccountStatusDto accountStatus;
    private List<UserReviewDto> userReviews;

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .phone(phone)
                .name(name)
                .roles(RoleDto.toRoles(roles))
                .goods(GoodsDto.toGoodsList(goods))
                .createdAt(createdAt)
                .deleteAt(deleteAt)
                .accountStatus(accountStatus.toAccountStatus())
                .userReviews(UserReviewDto.toUserReviews(userReviews))
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
                .goods(GoodsDto.fromGoodsList(user.getGoods()))
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