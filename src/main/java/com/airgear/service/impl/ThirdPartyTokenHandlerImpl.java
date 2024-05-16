package com.airgear.service.impl;

import com.airgear.dto.UserCreateRequest;
import com.airgear.model.CustomUserDetails;
import com.airgear.service.ThirdPartyDataHandler;
import com.airgear.service.ThirdPartyTokenHandler;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class ThirdPartyTokenHandlerImpl implements ThirdPartyTokenHandler {

    private final ThirdPartyDataHandler thirdPartyDataHandler;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Override
    public CustomUserDetails execute(UserCreateRequest request) {
        userService.create(request);
        return getCustomUserDetails(request);
    }

    @Override
    public CustomUserDetails execute(HttpServletRequest request) {
        UserCreateRequest userRequest = thirdPartyDataHandler.execute(request);
        return getCustomUserDetails(userRequest);
    }

    private CustomUserDetails getCustomUserDetails(UserCreateRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (CustomUserDetails) authentication.getPrincipal();
    }
}
