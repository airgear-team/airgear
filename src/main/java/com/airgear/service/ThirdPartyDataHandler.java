package com.airgear.service;

import com.airgear.dto.UserSaveRequest;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyDataHandler {

    UserSaveRequest execute(HttpServletRequest request);

}
