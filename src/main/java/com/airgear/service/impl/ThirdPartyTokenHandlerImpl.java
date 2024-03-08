package com.airgear.service.impl;

import com.airgear.security.ThirdPartyService;
import com.airgear.dto.UserDto;
import com.airgear.service.GoogleTokenHandler;
import com.airgear.service.ThirdPartyTokenHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.airgear.security.SecurityConstantsUI.THIRD_PARTY_SERVICE;
import static com.airgear.security.ThirdPartyService.valueOf;

/**
 * Implementation of {@link ThirdPartyTokenHandler} that handles third-party tokens.
 *
 * <p>This service extracts the third-party service type and token from the HTTP request headers
 * and delegates token handling to specific handlers based on the third-party service.</p>
 *
 * <p>Supported third-party services: Google, Apple, Facebook.</p>
 *
 * @author Oleksandr Tuleninov
 * @version 1.0
 */
@Service
public class ThirdPartyTokenHandlerImpl implements ThirdPartyTokenHandler {

    private final GoogleTokenHandler googleTokenHandler;

    public ThirdPartyTokenHandlerImpl(GoogleTokenHandler googleTokenHandler) {
        this.googleTokenHandler = googleTokenHandler;
    }

    /**
     * Executes token handling based on the third-party service specified in the request headers.
     *
     * @param request The HTTP servlet request.
     * @return User information extracted from the token.
     */
    @Override
    public UserDto execute(HttpServletRequest request) {
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
