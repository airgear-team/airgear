package com.airgear.controller;

import com.airgear.model.goods.Goods;
import com.airgear.model.User;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/", method = RequestMethod.GET)
    public List<User> getAllUsers(Authentication auth){
        log.info("auth name : {}", auth.getName());
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/{username}", method = RequestMethod.GET)
    public User getUserByUserName(@RequestParam String username){
        return userService.findByUsername(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','USER')")
    @RequestMapping(value="/{username}/goods", method = RequestMethod.GET)
    public Set<Goods> getAllGoodsBy(@RequestParam String username){
        Set<Goods> goodsSet = goodsService.getAllGoodsByUsername(username);
        return goodsSet;
    }

}