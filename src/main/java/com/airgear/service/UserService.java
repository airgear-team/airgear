package com.airgear.service;

import com.airgear.dto.GoodsCreateRequest;
import com.airgear.dto.SaveUserRequestDto;
import com.airgear.dto.UserDto;
import com.airgear.dto.UserExistDto;
import com.airgear.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserDto create(SaveUserRequestDto request);

    List<UserDto> findAll();

    List<UserDto> findActiveUsers();

    UserDto getUserByEmail(String email);

    UserExistDto isEmailExists(String username);

    void markUserAsPotentiallyScam(Long userId, boolean isScam);

    Set<GoodsCreateRequest> getFavoriteGoods(Authentication auth);

    User update(User user);

}
