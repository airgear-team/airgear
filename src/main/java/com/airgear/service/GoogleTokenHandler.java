package com.airgear.service;

import com.airgear.dto.LoginUserDto;
import com.airgear.dto.UserDto;

/**
 * The {@link GoogleTokenHandler} for handling Google tokens.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 1.0
 */
public interface GoogleTokenHandler {

    LoginUserDto execute(String token);

}
