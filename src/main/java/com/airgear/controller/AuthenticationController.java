package com.airgear.controller;

import com.airgear.dto.LoginUserDto;
import com.airgear.exception.UserUniquenessViolationException;
import com.airgear.exception.UserExceptions;
import com.airgear.model.AuthToken;
import com.airgear.model.User;
import com.airgear.security.TokenProvider;
import com.airgear.service.ThirdPartyTokenHandler;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth") // TODO використати класс шляхів Routes.AUTH
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;
    private final UserService userService;
    private final ThirdPartyTokenHandler tokenHandler;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUserDto userDto) throws AuthenticationException {

        if (userService.findByUsername(userDto.getUsername()) == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login or password is incorrect!");

        final String token = getToken(userDto);
        return ResponseEntity.ok(new AuthToken(token));
    }

    // TODO to use the special DTO for user saving
    // TODO "userService.checkForUserUniqueness(userDto)" - to use in a UserService in a method "public User save(UserDto user)"
    // TODO to refactor  "saveUser()" method
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody com.airgear.dto.UserDto userDto) {
        try {
            userService.checkForUserUniqueness(userDto);
            User user = userService.save(userDto);
            return ResponseEntity.ok(user);
        } catch (UserUniquenessViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/service/authenticate", method = RequestMethod.GET)
    public ResponseEntity<?> generateTokenFromThirdPartyService(HttpServletRequest request) {
        LoginUserDto user = tokenHandler.execute(request);

        if (userService.findByUsername(user.getUsername()) == null)
            throw new UsernameNotFoundException("User not found");

        final String token = getToken(user);
        return ResponseEntity.ok(new AuthToken(token));
    }

     // TODO зробити SecurityContextHolder.getContext().setAuthentication(authentication); у фільтрі 
    private String getToken(LoginUserDto user) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
        } catch (Exception e) {
            throw UserExceptions.userIsBlocked(user.getUsername());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(authentication);
    }
}
