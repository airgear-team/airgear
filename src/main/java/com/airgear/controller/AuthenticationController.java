package com.airgear.controller;

import com.airgear.dto.SignInRequest;
import com.airgear.dto.UserCreateRequest;
import com.airgear.entity.AuthToken;
import com.airgear.model.CustomUserDetails;
import com.airgear.security.TokenProvider;
import com.airgear.service.ActivationService;
import com.airgear.service.ThirdPartyTokenHandler;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ActivationService activationService;

    @PostMapping(value = "/authenticate")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = SignInRequest.class)))
    public AuthToken login(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return jwtTokenUtil.generateToken(userDetails);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken register(@RequestBody @Valid UserCreateRequest request) {
        return jwtTokenUtil.generateToken(tokenHandler.execute(request));
    }

    @GetMapping(value = "/service")
    public AuthToken generateTokenFromThirdPartyService(HttpServletRequest request) {
        return jwtTokenUtil.generateToken(tokenHandler.execute(request));
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token) {
        System.out.println("Token: " + token);
        boolean isActivated = activationService.activateUser(token);
        if (isActivated) {
            return ResponseEntity.ok("Account activated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid activation token.");
        }
    }
}
