package com.airgear.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Represents a token response containing subject (sub) and email information.
 * This class provides a simple data structure for holding token response data.
 *
 * <p>The fields {@code sub} and {@code email} contain the subject identifier and email address
 * retrieved from a token response.</p>
 *
 * @author Oleksandr Tuleninov
 * @version 1.0
 */

@Data
@Setter(AccessLevel.NONE)
public class TokenResponseDTO {
    String sub;
    String email;
}