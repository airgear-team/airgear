package com.airgear.service;

import com.airgear.dto.SaveUserRequestDto;
import com.airgear.model.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyTokenHandler {

    CustomUserDetails execute(SaveUserRequestDto request);

    CustomUserDetails execute(HttpServletRequest request);

}
