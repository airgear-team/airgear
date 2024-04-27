package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequestDto {

    @Email(message = "email must be a valid email string")
    @NotNull(message = "email must not be null")
    @Size(max = 255, message = "Email is too long, it must be no more than 255 characters long.")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password is too short, it must be at least 8 characters long.")
    @Size(max = 50, message = "Password is too long, it must be no more than 50 characters long.")
    private String password;

    @Pattern(regexp = "^\\+\\d{9,11}$", message = "phones must be in the format +XXXXXXXXXXX")
    private String phone;

    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = "^(?=.{2,50}$)[a-zA-Zа-яА-Я]+(?:\\s[a-zA-Zа-яА-Я]+)*$", message = "The name should contain between 2 and 50 characters, only Latin and Cyrillic characters, and should not contain special characters.")
    private String name;

}
