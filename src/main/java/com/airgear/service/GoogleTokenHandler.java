package com.airgear.service;

import com.airgear.dto.SaveUserRequestDto;

public interface GoogleTokenHandler {

    SaveUserRequestDto execute(String token);

}
