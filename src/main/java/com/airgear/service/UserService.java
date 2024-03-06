package com.airgear.service;

import com.airgear.model.User;
import com.airgear.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    void setAccountStatus(String username, long accountStatusId);

    User save(UserDto user);

    User update(User user);

    List<User> findAll();

    User findByUsername(String username);

    List<Map<String, Integer>> getUserGoodsCount(Pageable pageable);

    User addRole(String username, String role);

    User deleteRole(String username, String role);

}