package com.airgear.service;

import com.airgear.dto.UserCreateRequest;
import com.airgear.model.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyTokenHandler {

    CustomUserDetails execute(UserCreateRequest request);

    CustomUserDetails execute(HttpServletRequest request);

}
