package com.airgear.controller;

import com.airgear.dto.GoodsSearchResponse;
import com.airgear.dto.UserGetResponse;
import com.airgear.dto.UserExistResponse;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final GoodsService goodsService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<UserGetResponse>> getAllUsers(Authentication auth) {
        log.info("auth name : {}", auth.getName());
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<UserGetResponse> getUserByUserName(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','USER')")
    @RequestMapping(value = "/{username}/goods", method = RequestMethod.GET)
    public ResponseEntity<Set<GoodsSearchResponse>> getAllGoodsBy(@PathVariable String username) {
        return ResponseEntity.ok(goodsService.getAllGoodsByUsername(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{email}/isExists")
    public ResponseEntity<UserExistResponse> isEmailExists(Authentication auth,
                                                           @PathVariable String email) {
        log.info("auth name : {}", auth.getName());
        return ResponseEntity.ok(userService.isEmailExists(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/activeUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserGetResponse>> getAllActiveUsers(Authentication auth) {
        log.info("auth name : {}", auth.getName());
        return ResponseEntity.ok(userService.findActiveUsers());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','USER')")
    @GetMapping("/favorites")
    public Set<GoodsSearchResponse> getFavoriteGoods (Authentication auth) {
        log.info("auth name : {}", auth.getName());
        return userService.getFavoriteGoods(auth);
    }
}
