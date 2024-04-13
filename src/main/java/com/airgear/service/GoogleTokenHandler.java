package com.airgear.service;

import com.airgear.dto.SignInDto;

public interface GoogleTokenHandler {

    SignInDto execute(String token);

}
