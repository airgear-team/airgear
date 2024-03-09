package com.airgear.controller;

import com.airgear.dto.UserDto;
import com.airgear.model.AuthToken;
import com.airgear.model.User;
import com.airgear.security.TokenProvider;
import com.airgear.service.ThirdPartyTokenHandler;
import com.airgear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private ThirdPartyTokenHandler tokenHandler;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody UserDto userDto) throws AuthenticationException {
        final String token = getToken(userDto);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User saveUser(@RequestBody com.airgear.dto.UserDto user) {
        return userService.save(user);
    }

    //TODO delete it after testing
    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "{\"test\":\"test\"}";
    }

    @RequestMapping(value = "/service/authenticate", method = RequestMethod.GET)
    public ResponseEntity<?> generateTokenFromThirdPartyService(HttpServletRequest request) {
        UserDto user = tokenHandler.execute(request);

        if (userService.findByUsername(user.getUsername()) == null) userService.save(user);

        final String token = getToken(user);
        return ResponseEntity.ok(new AuthToken(token));
    }

    private String getToken(UserDto user) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(authentication);
    }
}