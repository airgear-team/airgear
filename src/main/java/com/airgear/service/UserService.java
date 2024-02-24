package com.airgear.service;

import com.airgear.model.User;
import com.airgear.dto.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);

    List<User> findAll();

    User findByUsername(String username);

    void setAccountStatus(String username, long accountStatusId);
}