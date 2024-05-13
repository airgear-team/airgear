package com.airgear.controller;

import com.airgear.dto.GoodsSearchResponse;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final GoodsService goodsService;

    @RequestMapping(value = "/{username}/goods", method = RequestMethod.GET)
    public Set<GoodsSearchResponse> getAllGoodsBy(@PathVariable String username) {
        return goodsService.getAllGoodsByUsername(username);
    }

    @GetMapping("/favorites")
    public Set<GoodsSearchResponse> getFavoriteGoods(@AuthenticationPrincipal String email) {
        return userService.getFavoriteGoods(email);
    }
}
