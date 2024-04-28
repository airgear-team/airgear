package com.airgear.service;

import com.airgear.dto.GoodsDto;
import com.airgear.dto.SaveUserRequestDto;
import com.airgear.dto.UserDto;
import com.airgear.dto.UserExistDto;
import com.airgear.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserDto create(SaveUserRequestDto request);

    UserDto getUserByEmail(String email);

    Set<GoodsDto> getFavoriteGoods(String email);

}
