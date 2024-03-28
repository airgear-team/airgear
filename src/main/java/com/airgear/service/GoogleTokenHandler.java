package com.airgear.service;

import com.airgear.dto.LoginUserDto;
import com.airgear.dto.UserDto;

public interface GoogleTokenHandler {

    LoginUserDto execute(String token);

}
