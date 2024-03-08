package com.airgear.dto;

import com.airgear.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;

    public User getUserFromDto() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setName(name);

        return user;
    }

    public static UserDto getDtoFromUser(User user) {
        return UserDto.builder()
                .username(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .name(user.getName())
                .build();
    }

}