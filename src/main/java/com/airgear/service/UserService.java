package com.airgear.service;

import com.airgear.dto.GoodsSearchResponse;
import com.airgear.dto.UserGetResponse;
import com.airgear.dto.UserCreateRequest;

import java.util.Set;

public interface UserService {

    UserGetResponse create(UserCreateRequest request);

    UserGetResponse getUserByEmail(String email);

    Set<GoodsSearchResponse> getFavoriteGoods(String email);

}
