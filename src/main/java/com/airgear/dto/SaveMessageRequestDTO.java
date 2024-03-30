package com.airgear.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class SaveMessageRequestDTO {

        @NotBlank(message = "text must not be empty")
        String text;

        @NotNull(message = "goods id must not be null")
        long goodsId;

        @NotNull(message = "user id must not be null")
        long userId;
}