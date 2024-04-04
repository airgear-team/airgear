package com.airgear.service.impl;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import com.airgear.config.AccountStatusConfig;
import com.airgear.dto.GoodsDto;
import com.airgear.dto.UserExistDto;
import com.airgear.exception.ChangeRoleException;
import com.airgear.exception.ForbiddenException;
import com.airgear.exception.UserExceptions;
import com.airgear.exception.UserUniquenessViolationException;
import com.airgear.mapper.GoodsMapper;
import com.airgear.mapper.UserMapper;
import com.airgear.model.AccountStatus;
import com.airgear.model.Roles;
import com.airgear.model.email.EmailMessage;
import com.airgear.repository.AccountStatusRepository;
import com.airgear.repository.UserRepository;
import com.airgear.model.User;
import com.airgear.dto.UserDto;
import com.airgear.service.EmailService;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.airgear.utils.Constants.ROLE_ADMIN_NAME;

@Service(value = "userService")
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final UserMapper userMapper;
    private final GoodsMapper goodsMapper;
    private final EmailService emailService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString())));
        return authorities;
    }

    public List<UserDto> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return userMapper.toDtoList(users);
    }

    public List<UserDto> findActiveUsers() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> user.getAccountStatus() != null && user.getAccountStatus().getStatusName().equals("ACTIVE")).toList();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto findByUsername(String username) {
        return userMapper.toDto(userRepository.findByUsername(username));
    }


    @Override
    public UserExistDto isUsernameExists(String username) {
        return UserExistDto.builder()
                .username(username)
                .exist(userRepository.existsByUsername(username))
                .build();
    }

    @Override
    public void setAccountStatus(String username, long accountStatusId) {
        User user = userRepository.findByUsername(username);
        if (user == null || user.getAccountStatus().getId() == accountStatusId) {
            throw new ForbiddenException("User not found or was already deleted");
        }

        AccountStatus accountStatus = accountStatusRepository.findById(accountStatusId).orElseThrow(() -> new RuntimeException("Account status not found"));

        if (accountStatus.getId() == 2) {
            user.setAccountStatus(accountStatus);
            userRepository.save(user);
            sendFarewellEmail(Set.of(user.getEmail()));
        } else {
            user.setAccountStatus(accountStatus);
            userRepository.save(user);
        }
    }

    private void sendFarewellEmail(Set<String> userEmails) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject("We're sad to see you go!");
        emailMessage.setMessage("Hello,\n\nWe noticed that your account is now inactive. We're sorry to see you leave! If there was any issue with our service, or if you have any feedback, please let us know. We hope to serve you again in the future.\n\nBest,\nThe Airgear Team");

        emailService.sendMail(emailMessage, userEmails);
    }

    @Override
    public User save(UserDto user) {
        if(user.getPhone()!=null && userRepository.existsByPhone(user.getPhone())){
            throw new ForbiddenException("Other user with phone number exists!");
        }
        Set<Roles> roleSet = new HashSet<>();
        roleSet.add(Roles.USER);
        User newUser = userMapper.toModel(user);
        newUser.setRoles(roleSet);
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
        Set<Roles> roles = user.getRoles();
        roles.add(Roles.ADMIN);
        user.setRoles(roles);
        return update(user);
    }

    @Override
    public User deleteRole(String username, String role) {
        User user = userRepository.findByUsername(username);
        Set<Roles> roles = user.getRoles();
        roles.remove(Roles.ADMIN);
        if (roles.isEmpty()) {
            roles.add(Roles.USER);
        }
        return update(user);
    }

    @Override
    public UserDto appointRole(String username, Roles role) {
        User user = userRepository.findByUsername(username);
        user.getRoles().add(role);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto removeRole(String username, Roles role) {
        User user = userRepository.findByUsername(username);
        user.getRoles().remove(role);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    @Override
    public void markUserAsPotentiallyScam(Long userId, boolean isScam) {
        userRepository.updateIsPotentiallyScamStatus(userId, isScam);
    }

    @Override
    public Set<GoodsDto> getFavoriteGoods(Authentication auth) {
        UserDto user = this.findByUsername(auth.getName());
        return goodsMapper.toDtoSet(userRepository.getFavoriteGoodsByUser(user.getId()));
    }
    // TODO to use this method inside the "public User save(UserDto user)" method for better performance
    public void checkForUserUniqueness(UserDto userDto) throws UserUniquenessViolationException {
        boolean usernameExists = userRepository.existsByUsername(userDto.getUsername());
        boolean emailExists = userRepository.existsByEmail(userDto.getEmail());
        boolean phoneExists = userRepository.existsByPhone(userDto.getPhone());

        if (usernameExists) {
            throw new UserUniquenessViolationException("Username already exists.");
        }
        if (emailExists) {
            throw new UserUniquenessViolationException("Email already exists.");
        }
        if (phoneExists) {
            throw new UserUniquenessViolationException("Phone number already exists.");
        }
    }

    @Override
    @Transactional
    public UserDto blockUser(Long userId) {
        User user = getUserById(userId);
        user.setAccountStatus(accountStatusRepository.findByStatusName(AccountStatusConfig.INACTIVE.name()));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto unblockUser(Long userId) {
        User user = getUserById(userId);
        user.setAccountStatus(accountStatusRepository.findByStatusName(AccountStatusConfig.ACTIVE.name()));
        return userMapper.toDto(user);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserExceptions.userNotFound(userId));
    }


    @Override
    public void deleteAccount(String username) {
        User user = userRepository.findByUsername(username);
        if (user.getUsername().equals(username) || user.getRoles().stream().anyMatch(role -> role == Roles.ADMIN)) {
            setAccountStatus(username, 2);
        } else throw new ForbiddenException("Insufficient privileges");
    }

    @Override
    public void accessToRoleChange(String executor, Roles role) {
        UserDto executorUser = findByUsername(executor);
        if (!executorUser.getRoles().contains(role) && role.toString().equalsIgnoreCase(ROLE_ADMIN_NAME)) {
            throw new ChangeRoleException("Access denied");
        }
    }
}