package com.airgear.service;

import com.airgear.dto.GoodsDto;
import com.airgear.dto.UserExistDto;
import com.airgear.exception.UserUniquenessViolationException;
import com.airgear.model.Roles;
import com.airgear.model.User;
import com.airgear.dto.UserDto;
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

    List<UserDto> findAll();

    List<UserDto> findActiveUsers();

    UserDto findByUsername(String username);

    UserExistDto isUsernameExists(String username);

    List<Map<String, Integer>> getUserGoodsCount(Pageable pageable);

    UserDto appointRole(String username, Roles role);

    UserDto removeRole(String username, Roles role);

    User addRole(String username, String role);

    User deleteRole(String username, String role);

    void markUserAsPotentiallyScam(Long userId, boolean isScam);

    int countNewUsersBetweenDates(String start, String end);

    Set<GoodsDto> getFavoriteGoods(Authentication auth);

    void checkForUserUniqueness(UserDto userDto) throws UserUniquenessViolationException;

    int countDeletedUsersBetweenDates(OffsetDateTime startDate, OffsetDateTime endDate);

    UserDto blockUser(Long userId);

    UserDto unblockUser(Long userId);

    void deleteAccount(String username);

    void accessToRoleChange(String executor, Roles role);
}