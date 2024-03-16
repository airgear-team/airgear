package com.airgear.service;

import com.airgear.model.User;
import com.airgear.dto.UserDto;
import com.airgear.model.goods.Goods;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    void setAccountStatus(String username, long accountStatusId);

    User save(UserDto user);

    User update(User user);

    List<User> findAll();

    List<User> findActiveUsers();

    User findByUsername(String username);

    boolean isUsernameExists(String username);

    List<Map<String, Integer>> getUserGoodsCount(Pageable pageable);

    User apponintModerator(String username);

    User removeModerator(String username);

    User addRole(String username, String role);

    User deleteRole(String username, String role);

    void markUserAsPotentiallyScam(Long userId, boolean isScam);

    int countNewUsersBetweenDates(OffsetDateTime start, OffsetDateTime end);

    Set<Goods> getFavoriteGoods(Authentication auth);
}