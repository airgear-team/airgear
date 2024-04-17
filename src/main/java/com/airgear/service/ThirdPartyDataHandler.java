package com.airgear.service;

import com.airgear.dto.SaveUserRequestDto;

import javax.servlet.http.HttpServletRequest;

public interface ThirdPartyDataHandler {

    SaveUserRequestDto execute(HttpServletRequest request);

}
