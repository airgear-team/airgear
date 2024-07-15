package com.airgear.service;

import com.airgear.dto.UserCreateRequest;

public interface GoogleTokenHandler {

    UserCreateRequest execute(String token);

}
