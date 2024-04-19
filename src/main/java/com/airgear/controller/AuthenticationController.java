package com.airgear.controller;

import com.airgear.dto.SaveUserRequestDto;
import com.airgear.dto.SignInDto;
import com.airgear.entity.AuthToken;
import com.airgear.model.CustomUserDetails;
import com.airgear.security.TokenProvider;
import com.airgear.service.ThirdPartyTokenHandler;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final TokenProvider jwtTokenUtil;
    private final ThirdPartyTokenHandler tokenHandler;

    @PostMapping(value = "/authenticate")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = SignInDto.class)))
    public AuthToken login(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return jwtTokenUtil.generateToken(userDetails);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken register(@RequestBody @Valid SaveUserRequestDto request) {
        return jwtTokenUtil.generateToken(tokenHandler.execute(request));
    }

    @GetMapping(value = "/service")
    public AuthToken generateTokenFromThirdPartyService(HttpServletRequest request) {
        return jwtTokenUtil.generateToken(tokenHandler.execute(request));
    }
}
