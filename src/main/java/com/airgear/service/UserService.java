package com.airgear.service;

import com.airgear.dto.*;
import com.airgear.dto.UserSaveRequest;
import com.airgear.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserGetResponse create(UserSaveRequest request);

    List<UserGetResponse> findAll();

    List<UserGetResponse> findActiveUsers();

    UserGetResponse getUserByEmail(String email);

    UserExistResponse isEmailExists(String username);

    void markUserAsPotentiallyScam(Long userId, boolean isScam);

    Set<GoodsSearchResponse> getFavoriteGoods(Authentication auth);

    User update(User user);

}
