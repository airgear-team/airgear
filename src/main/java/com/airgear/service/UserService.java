package com.airgear.service;

import com.airgear.model.User;
import com.airgear.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    void setAccountStatus(String username, long accountStatusId);

    User save(UserDto user);

    List<User> findAll();

    User findByUsername(String username);

    List<Map<String, Integer>> getUserGoodsCount(Pageable pageable);

    User apponintModerator(String username);

    User removeModerator(String username);

}