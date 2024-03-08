package com.airgear.service;

import com.airgear.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@link ThirdPartyTokenHandler} that handles third-party tokens.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 1.0
 */
public interface ThirdPartyTokenHandler {

    UserDto execute(HttpServletRequest request);
    
}
