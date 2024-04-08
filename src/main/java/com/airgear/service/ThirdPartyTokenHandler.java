package com.airgear.service;

import com.airgear.dto.SignInDto;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyTokenHandler {

    SignInDto execute(HttpServletRequest request);
    
}
