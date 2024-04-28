package com.airgear.service;

import com.airgear.dto.UserSaveRequest;

public interface GoogleTokenHandler {

    UserSaveRequest execute(String token);

}
