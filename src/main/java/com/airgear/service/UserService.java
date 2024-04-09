package com.airgear.service;

import com.airgear.dto.GoodsDto;
import com.airgear.dto.SaveUserRequestDto;
import com.airgear.dto.UserDto;
import com.airgear.dto.UserExistDto;
import com.airgear.exception.UserUniquenessViolationException;
import com.airgear.model.Role;
import com.airgear.model.User;
import com.airgear.model.UserStatus;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface UserService {

    void setAccountStatus(String email, UserStatus status);

    UserDto create(SaveUserRequestDto request);

    User update(User user);

    List<UserDto> findAll();

    List<UserDto> findActiveUsers();

    UserDto getUserByEmail(String email);

    UserExistDto isEmailExists(String username);

    UserDto appointRole(String username, Role role);

    UserDto removeRole(String username, Role role);

    User addRole(String email, String role);

    User deleteRole(String email, String role);

    void markUserAsPotentiallyScam(Long userId, boolean isScam);

    Set<GoodsDto> getFavoriteGoods(Authentication auth);

    void checkForUserUniqueness(UserDto userDto) throws UserUniquenessViolationException;

    UserDto blockUser(Long userId);

    UserDto unblockUser(Long userId);

    void deleteAccount(String email);

    void accessToRoleChange(String executor, Role role);
}