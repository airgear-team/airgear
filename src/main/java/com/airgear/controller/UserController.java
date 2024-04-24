package com.airgear.controller;

import com.airgear.dto.GoodsCreateRequest;
import com.airgear.dto.UserDto;
import com.airgear.dto.UserExistDto;
import com.airgear.model.Role;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<List<UserDto>> getAllUsers(Authentication auth) {
        log.info("auth name : {}", auth.getName());
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByUserName(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','USER')")
    @RequestMapping(value = "/{username}/goods", method = RequestMethod.GET)
    public ResponseEntity<Set<GoodsCreateRequest>> getAllGoodsBy(@PathVariable String username) {
        return ResponseEntity.ok(goodsService.getAllGoodsByUsername(username));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(value = "/{email}")
    public ResponseEntity<String> deleteAccount(@PathVariable String email) {
        userService.deleteAccount(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{email}/roles")
    @Validated
    public ResponseEntity<UserDto> appointRole(Authentication auth,
                                               @PathVariable String email,
                                               @RequestBody Role role) {
        userService.accessToRoleChange(auth.getName(), role);
        return ResponseEntity.ok(userService.appointRole(email, role));
    }

    @DeleteMapping(value = "/{email}/roles")
    @Validated
    public ResponseEntity<String> removeRole(Authentication auth,
                                             @PathVariable String email,
                                             @RequestBody Role role) {
        userService.accessToRoleChange(auth.getName(), role);
        userService.removeRole(email, role);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{email}/isExists")
    public ResponseEntity<UserExistDto> isEmailExists(Authentication auth,
                                                      @PathVariable String email) {
        log.info("auth name : {}", auth.getName());
        return ResponseEntity.ok(userService.isEmailExists(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/activeUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllActiveUsers(Authentication auth) {
        log.info("auth name : {}", auth.getName());
        return ResponseEntity.ok(userService.findActiveUsers());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','USER')")
    @GetMapping("/favorites")
    public Set<GoodsCreateRequest> getFavoriteGoods (Authentication auth) {
        log.info("auth name : {}", auth.getName());
        return userService.getFavoriteGoods(auth);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PatchMapping(value = "/{userId}/block")
    public ResponseEntity<UserDto> blockUser (@PathVariable Long userId) {
        return ResponseEntity.ok(userService.blockUser(userId));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "/{userId}/unblock")
    public ResponseEntity<UserDto> unblockUser (@PathVariable Long userId) {
        return ResponseEntity.ok(userService.unblockUser(userId));
    }
}