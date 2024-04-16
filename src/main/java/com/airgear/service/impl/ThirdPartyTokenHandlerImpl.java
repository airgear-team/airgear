package com.airgear.service.impl;

import com.airgear.dto.SignInDto;
import com.airgear.model.CustomUserDetails;
import com.airgear.security.ThirdPartyService;
import com.airgear.service.GoogleTokenHandler;
import com.airgear.service.ThirdPartyTokenHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.airgear.security.SecurityConstants.THIRD_PARTY_SERVICE;
import static com.airgear.security.ThirdPartyService.valueOf;

@Service
@AllArgsConstructor
public class ThirdPartyTokenHandlerImpl implements ThirdPartyTokenHandler {

    private final AuthenticationManager authenticationManager;
    private final GoogleTokenHandler googleTokenHandler;

    @Override
    public CustomUserDetails execute(HttpServletRequest request) {
        String thirdPartyService = request.getHeader(THIRD_PARTY_SERVICE);
        ThirdPartyService service = valueOf(thirdPartyService);
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        SignInDto user = switch (service) {
            case GOOGLE -> googleTokenHandler.execute(token);
            case APPLE -> null;
            case FACEBOOK -> null;
        };

        assert user != null;
        return getCustomUserDetails(user);
    }

    private CustomUserDetails getCustomUserDetails(SignInDto user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (CustomUserDetails) authentication.getPrincipal();
    }
}
