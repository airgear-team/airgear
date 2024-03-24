package com.airgear.service;

import com.airgear.dto.RoleDto;
import com.airgear.dto.UserExistDto;
import com.airgear.model.User;
import com.airgear.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    void setAccountStatus(String username, long accountStatusId);

    User save(UserDto user);

    User update(User user);

    List<UserDto> findAll();

    List<UserDto> findActiveUsers();

    UserDto findByUsername(String username);

    UserExistDto isUsernameExists(String username);

    List<Map<String, Integer>> getUserGoodsCount(Pageable pageable);

    UserDto appointRole(String username, RoleDto role);

    UserDto removeRole(String username, RoleDto role);

    User addRole(String username, String role);

    User deleteRole(String username, String role);

    void markUserAsPotentiallyScam(Long userId, boolean isScam);

    int countNewUsersBetweenDates(String start, String end);

    void deleteAccount(String username);

    void accessToRoleChange(String executor, RoleDto role);

    void checkForUserUniqueness(UserDto userDto);
}