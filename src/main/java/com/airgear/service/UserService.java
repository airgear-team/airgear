package com.airgear.service;

import com.airgear.dto.GoodsDto;
import com.airgear.dto.RoleDto;
import com.airgear.dto.UserExistDto;
import com.airgear.exception.UserUniquenessViolationException;
import com.airgear.model.User;
import com.airgear.dto.UserDto;
import com.airgear.model.UserStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    void setAccountStatus(String username, UserStatus status);

    User save(UserDto user);

    User update(User user);

    List<UserDto> findAll();

    List<UserDto> findActiveUsers();

    UserDto findByUsername(String username);

    UserExistDto isUsernameExists(String username);

    UserDto appointRole(String username, RoleDto role);

    UserDto removeRole(String username, RoleDto role);

    User addRole(String username, String role);

    User deleteRole(String username, String role);

    void markUserAsPotentiallyScam(Long userId, boolean isScam);

    Set<GoodsDto> getFavoriteGoods(Authentication auth);

    void checkForUserUniqueness(UserDto userDto) throws UserUniquenessViolationException;

    UserDto blockUser(Long userId);

    UserDto unblockUser(Long userId);

    void deleteAccount(String username);

    void accessToRoleChange(String executor, RoleDto role);
}