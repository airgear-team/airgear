package com.airgear.service.impl;

import com.airgear.dto.UserSaveRequest;
import com.airgear.security.ThirdPartyService;
import com.airgear.service.GoogleTokenHandler;
import com.airgear.service.ThirdPartyDataHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.airgear.security.SecurityConstants.THIRD_PARTY_SERVICE;
import static com.airgear.security.ThirdPartyService.valueOf;

@Service
@AllArgsConstructor
public class ThirdPartyDataHandlerImpl implements ThirdPartyDataHandler {

    private final GoogleTokenHandler googleTokenHandler;

    @Override
    public UserSaveRequest execute(HttpServletRequest request) {
        String thirdPartyService = request.getHeader(THIRD_PARTY_SERVICE);
        ThirdPartyService service = valueOf(thirdPartyService);
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        return switch (service) {
            case GOOGLE -> googleTokenHandler.execute(token);
            case APPLE -> null;
            case FACEBOOK -> null;
        };
    }
}
