package com.airgear.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class TokenResponseDTO {
    String sub;
    String email;
}