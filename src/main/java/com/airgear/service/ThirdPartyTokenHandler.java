package com.airgear.service;

import com.airgear.dto.LoginUserDto;
import com.airgear.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyTokenHandler {

    LoginUserDto execute(HttpServletRequest request);
    
}
