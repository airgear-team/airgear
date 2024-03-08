package com.airgear.dto;

/**
 * Represents a token response containing subject (sub) and email information.
 * This record provides a simple data structure for holding token response data.
 *
 * <p>The fields {@code sub} and {@code email} contain the subject identifier and email address
 * retrieved from a token response.</p>
 *
 * @param sub   The subject identifier extracted from the token response.
 * @param email The email address extracted from the token response.
 *
 * @author Oleksandr Tuleninov
 * @version 1.0
 */
public record TokenResponse(String sub,
                            String email){
}
