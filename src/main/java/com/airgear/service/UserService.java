package com.airgear.service;

import com.airgear.model.User;
import com.airgear.dto.UserDto;

import java.util.List;

public interface UserService {

    void setAccountStatus(String username, long accountStatusId);

    User save(UserDto user);

    User save(User user);

    List<User> findAll();

    User findByUsername(String username);

}