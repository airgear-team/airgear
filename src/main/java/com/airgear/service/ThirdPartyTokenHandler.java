package com.airgear.service;

import com.airgear.model.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyTokenHandler {

    CustomUserDetails execute(HttpServletRequest request);

}
