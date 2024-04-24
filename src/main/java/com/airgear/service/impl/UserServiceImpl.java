package com.airgear.service.impl;

import com.airgear.dto.GoodsCreateRequest;
import com.airgear.dto.SaveUserRequestDto;
import com.airgear.dto.UserDto;
import com.airgear.dto.UserExistDto;
import com.airgear.exception.UserExceptions;
import com.airgear.mapper.GoodsMapper;
import com.airgear.mapper.UserMapper;
import com.airgear.model.CustomUserDetails;
import com.airgear.model.Role;
import com.airgear.model.User;
import com.airgear.model.UserStatus;
import com.airgear.repository.UserRepository;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@Service(value = "userService")
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final GoodsMapper goodsMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email '" + email + "' not found"));

        return new CustomUserDetails(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return userMapper.toDtoList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findActiveUsers() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> user.getStatus() != null && user.getStatus().equals(UserStatus.ACTIVE)).toList();
        return userMapper.toDtoList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = getUser(email);
        return userMapper.toDto(user);
    }

    @Override
    public UserExistDto isEmailExists(String email) {
        return UserExistDto.builder()
                .username(email)
                .exist(userRepository.existsByEmail(email))
                .build();
    }

    @Override
    public UserDto create(SaveUserRequestDto request) {
        validateUniqueFields(request);
        User user = save(request);
        return userMapper.toDto(user);
    }

    @Override
    public void markUserAsPotentiallyScam(Long userId, boolean isScam) {
        userRepository.updateIsPotentiallyScamStatus(userId, isScam);
    }

    @Override
    public Set<GoodsCreateRequest> getFavoriteGoods(Authentication auth) {
        User user = getUser(auth.getName());
        return goodsMapper.toDtoSet(userRepository.getFavoriteGoodsByUser(user.getId()));
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    private void validateUniqueFields(SaveUserRequestDto request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw UserExceptions.duplicateEmail(email);
        }
        String phone = request.getPhone();
        if (userRepository.existsByPhone(phone)) {
            throw UserExceptions.duplicatePhone(phone);
        }
    }

    private User save(SaveUserRequestDto request) {
        var user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setName(request.getName());
        user.setRoles(createRoles());
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(OffsetDateTime.now());
        userRepository.save(user);
        return user;
    }

    private Set<Role> createRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        return roles;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserExceptions.userNotFound(email));
    }
}
