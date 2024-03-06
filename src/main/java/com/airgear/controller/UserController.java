package com.airgear.controller;

import com.airgear.exception.ForbiddenException;
import com.airgear.model.goods.Goods;
import com.airgear.model.User;
import com.airgear.repository.AccountStatusRepository;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private AccountStatusRepository accountStatusRepository;
    private static final int MAX_LIMIT = 500;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers(Authentication auth) {
        log.info("auth name : {}", auth.getName());
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getUserByUserName(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','USER')")
    @RequestMapping(value = "/{username}/goods", method = RequestMethod.GET)
    public Set<Goods> getAllGoodsBy(@PathVariable String username) {
        Set<Goods> goodsSet = goodsService.getAllGoodsByUsername(username);
        return goodsSet;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(value = "/{username}")
    public ResponseEntity<String> deleteAccount(Authentication auth, @PathVariable String username) {
        User user = userService.findByUsername(auth.getName());
        if (user.getUsername().equals(username) || user.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName()))) {
            userService.setAccountStatus(username, 2);
            return ResponseEntity.noContent().build();
        }
        throw new ForbiddenException("Insufficient privileges");
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/goods-count")
    public List<Map<String, Integer>> getTopUserGoodsCount(@RequestParam(required = false, defaultValue = "30") int limit) {
        if (limit > MAX_LIMIT) {
            throw new IllegalArgumentException("Limit exceeds maximum value of 500");
        }
        //it will be possible to implement pagination on the frontend in the future
        Pageable pageable = PageRequest.of(0, limit);

        return userService.getUserGoodsCount(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{username}/isExists")
    public Boolean isUsernameExists(Authentication auth, @PathVariable String username) {
        log.info("auth name : {}", auth.getName());
        return userService.isUsernameExists(username);
    }

}