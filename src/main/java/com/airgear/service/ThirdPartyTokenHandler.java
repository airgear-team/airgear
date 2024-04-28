package com.airgear.service;

import com.airgear.dto.UserSaveRequest;
import com.airgear.model.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyTokenHandler {

    CustomUserDetails execute(UserSaveRequest request);

    CustomUserDetails execute(HttpServletRequest request);

}
