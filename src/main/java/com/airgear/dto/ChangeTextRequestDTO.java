package com.airgear.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Setter(AccessLevel.NONE)
public class ChangeTextRequestDTO {

        @NotBlank(message = "text must not be empty")
        String text;
}