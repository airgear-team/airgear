package com.airgear.service.impl;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import com.airgear.dto.RoleDto;
import com.airgear.dto.UserExistDto;
import com.airgear.exception.ChangeRoleException;
import com.airgear.exception.ForbiddenException;
import com.airgear.repository.AccountStatusRepository;
import com.airgear.repository.UserRepository;
import com.airgear.model.Role;
import com.airgear.model.User;
import com.airgear.dto.UserDto;
import com.airgear.service.RoleService;
import com.airgear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.airgear.utils.Constants.ROLE_ADMIN_NAME;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    public UserServiceImpl(RoleService roleService, UserRepository userRepository, AccountStatusRepository accountStatusRepository, BCryptPasswordEncoder bcryptEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.accountStatusRepository = accountStatusRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<UserDto> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return UserDto.fromUsers(users);
    }

    public List<UserDto> findActiveUsers() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> user.getAccountStatus() != null && user.getAccountStatus().getStatusName().equals("ACTIVE")).toList();
        return UserDto.fromUsers(users);
    }

    @Override
    public UserDto findByUsername(String username) {
        return UserDto.fromUser(userRepository.findByUsername(username));
    }


    @Override
    public UserExistDto isUsernameExists(String username) {
        return UserExistDto.builder()
                .username(username)
                .exist(userRepository.existsByUsername(username))
                .build();
    }


    @Override
    public List<Map<String, Integer>> getUserGoodsCount(Pageable pageable) {
        return userRepository.findUserGoodsCount(pageable);
    }

    @Override
    public void setAccountStatus(String username, long accountStatusId) {
        User user = userRepository.findByUsername(username);
        if (user == null || user.getAccountStatus().getId() == accountStatusId) {
            throw new ForbiddenException("User not found or was already deleted");
        }
        if (accountStatusId == 2L) {
            user.setDeleteAt(OffsetDateTime.now());
        }
        userRepository.setAccountStatusId(accountStatusId, user.getId());
    }

    @Override
    public User save(UserDto user) {
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(RoleDto.fromRoles(roleSet));
        User newUser = user.toUser();
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setCreatedAt(OffsetDateTime.now());
        newUser.setAccountStatus(accountStatusRepository.findByStatusName("ACTIVE"));
        return userRepository.save(newUser);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User addRole(String username, String role) {
        User user = userRepository.findByUsername(username);
        Set<Role> roles = user.getRoles();
        roles.add(roleService.findByName("ADMIN"));
        user.setRoles(roles);
        return update(user);
    }

    @Override
    public User deleteRole(String username, String role) {
        User user = userRepository.findByUsername(username);
        Set<Role> roles = user.getRoles();
        roles.remove(roleService.findByName("ADMIN"));
        if (roles.isEmpty()) {
            roles.add(roleService.findByName("USER"));
        }
        return update(user);
    }

    @Override
    public UserDto appointRole(String username, RoleDto role) {
        User user = userRepository.findByUsername(username);
        user.getRoles().add(role.toRole());
        User updatedUser = userRepository.save(user);
        return UserDto.fromUser(updatedUser);
    }

    @Override
    public UserDto removeRole(String username, RoleDto role) {
        User user = userRepository.findByUsername(username);
        user.getRoles().remove(role.toRole());
        User updatedUser = userRepository.save(user);
        return UserDto.fromUser(updatedUser);
    }

    @Transactional
    @Override
    public void markUserAsPotentiallyScam(Long userId, boolean isScam) {
        userRepository.updateIsPotentiallyScamStatus(userId, isScam);
    }

    @Override
    public int countNewUsersBetweenDates(String start, String end) {
        OffsetDateTime startDate = OffsetDateTime.parse(start);
        OffsetDateTime endDate = OffsetDateTime.parse(end);
        return userRepository.countByCreatedAtBetween(startDate, endDate);
    }

    @Override
    public void deleteAccount(String username) {
        User user = userRepository.findByUsername(username);
        if (user.getUsername().equals(username) || user.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName()))) {
            setAccountStatus(username, 2);
        } else throw new ForbiddenException("Insufficient privileges");
    }

    @Override
    public void accessToRoleChange(String executor, RoleDto role) {
        UserDto executorUser = findByUsername(executor);
        if (!executorUser.getRoles().contains(role) && role.getName().equalsIgnoreCase(ROLE_ADMIN_NAME)) {
            throw new ChangeRoleException("Access denied");
        }
    }
}