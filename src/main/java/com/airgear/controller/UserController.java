package com.airgear.controller;

import com.airgear.dto.UserDto;
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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    //TODO
    // 1. Винести всю логіку в сервіси. Лишити тільки викликання одного методу сервісу
    // 2. Перенести залежностів в конструктор
    // 3. Винисти константи в клас utils.Constants та дати їх більш конкретну назву
    // 4. Зробити власні ексепшени

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
        return goodsService.getAllGoodsByUsername(username);
    }

    // TODO Зробити власні ексепшени
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

    // TODO Зробити власні ексепшени
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

    //TODO розібратись з цими двома методами.
    // Скоріш зав все просто поєднати логіку щоб був тільки один ендпоїнт
    // За допомогою якого ми можемо авати ролі
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PatchMapping(value = "/{username}/role")
    public ResponseEntity<UserDto> changeRole(@PathVariable String username,
                                              @RequestBody String status) {
        User user;
        switch (status.toLowerCase()) {
            case "appoint" -> user = userService.apponintModerator(username);
            case "remove" -> user = userService.removeModerator(username);
            default -> throw new IllegalArgumentException("Status not found");
        }
        return ResponseEntity.ok(UserDto.fromUser(user));
    }

    // TODO Зробити власні ексепшени
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{username}/role1", method = RequestMethod.PATCH)
    public User changeRoleAdmin(@PathVariable String username, @RequestParam String act) {
        if (act.equals("add"))
            return userService.addRole(username, "ADMIN");
        else if (act.equals("delete"))
            return userService.deleteRole(username,"ADMIN");
        else
            throw new RuntimeException("Don't correct field: act! Choose: add or delete!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count")
    public ResponseEntity<?> countNewUsers(@RequestParam("start") String start, @RequestParam("end") String end) {
        OffsetDateTime startDate = OffsetDateTime.parse(start);
        OffsetDateTime endDate = OffsetDateTime.parse(end);
        int count = userService.countNewUsersBetweenDates(startDate, endDate);
        return ResponseEntity.ok().body(Map.of("count", count));
    }

    // TODO Створити власну модель а не повертати просто true
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{username}/isExists")
    public Boolean isUsernameExists(Authentication auth, @PathVariable String username) {
        log.info("auth name : {}", auth.getName());
        return userService.isUsernameExists(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/activeUsers", method = RequestMethod.GET)
    public List<User> getAllActiveUsers(Authentication auth) {
        log.info("auth name : {}", auth.getName());
        return userService.findActiveUsers();
    }

}