package com.airgear.service;

import com.airgear.dto.UserCreateRequest;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyDataHandler {

    UserCreateRequest execute(HttpServletRequest request);

}
