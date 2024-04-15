package com.airgear.controller;

import com.airgear.dto.SaveUserRequestDto;
import com.airgear.dto.SignInDto;
import com.airgear.dto.UserDto;
import com.airgear.entity.AuthToken;
import com.airgear.security.CustomUserDetails;
import com.airgear.security.TokenProvider;
import com.airgear.service.ThirdPartyTokenHandler;
import com.airgear.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final TokenProvider jwtTokenUtil;
    private final UserService userService;
    private final ThirdPartyTokenHandler tokenHandler;

    @PostMapping(
            value = "/authenticate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = SignInDto.class)))
    public AuthToken login(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return jwtTokenUtil.generateToken(userDetails);
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> register(@RequestBody @Valid SaveUserRequestDto request,
                                            UriComponentsBuilder ucb) {
        UserDto response = userService.create(request);

        return ResponseEntity
                .created(ucb.path("/auth/{id}").build(response.getId()))
                .body(response);
    }

    @GetMapping(
            value = "/service",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AuthToken generateTokenFromThirdPartyService(HttpServletRequest request) {
        return jwtTokenUtil.generateToken(tokenHandler.execute(request));
    }
}
