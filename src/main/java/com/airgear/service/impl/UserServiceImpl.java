package com.airgear.service.impl;

import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import com.airgear.config.AccountStatusConfig;
import com.airgear.dto.GoodsDto;
import com.airgear.dto.RoleDto;
import com.airgear.dto.UserExistDto;
import com.airgear.exception.UserExceptions;
import com.airgear.mapper.GoodsMapper;
import com.airgear.mapper.RoleMapper;
import com.airgear.mapper.UserMapper;
import com.airgear.model.AccountStatus;
import com.airgear.model.email.EmailMessage;
import com.airgear.repository.AccountStatusRepository;
import com.airgear.repository.UserRepository;
import com.airgear.model.Role;
import com.airgear.model.User;
import com.airgear.dto.UserDto;
import com.airgear.service.EmailService;
import com.airgear.service.RoleService;
import com.airgear.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.airgear.utils.Constants.ROLE_ADMIN_NAME;

@Service(value = "userService")
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final UserMapper userMapper;
    private final GoodsMapper goodsMapper;
    private final RoleMapper roleMapper;
    private final EmailService emailService;

    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw UserExceptions.userNotFoundAuthorized(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
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
    public void setAccountStatus(String username, Long accountStatusId) {
        User user = userRepository.findByUsername(username);
        if (user == null || user.getAccountStatus().getId() == accountStatusId) {
            throw UserExceptions.userNotFound(username);
        }

        AccountStatus accountStatus = accountStatusRepository.findById(accountStatusId).orElseThrow(() -> UserExceptions.userUniqueness("Account status", accountStatusId.toString()));

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
        checkForUserUniqueness(user);
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleMapper.toDtoSet(roleSet));
        User newUser = userMapper.toModel(user);
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
        user.getRoles().add(roleMapper.toModel(role));
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto removeRole(String username, RoleDto role) {
        User user = userRepository.findByUsername(username);
        user.getRoles().remove(roleMapper.toModel(role));
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

    public void checkForUserUniqueness(UserDto userDto){
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw UserExceptions.userUniqueness("Username", userDto.getUsername());
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw UserExceptions.userUniqueness("Email", userDto.getEmail());
        }
        if (userRepository.existsByPhone(userDto.getPhone())) {
            throw UserExceptions.userUniqueness("Phone number", userDto.getPhone());
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
        if (user.getUsername().equals(username) || user.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName()))) {
            setAccountStatus(username, 2L);
        } else throw UserExceptions.AccessDenied("delete account");
    }

    @Override
    public void accessToRoleChange(String executor, RoleDto role) {
        UserDto executorUser = findByUsername(executor);
        if (!executorUser.getRoles().contains(role) && role.getName().equalsIgnoreCase(ROLE_ADMIN_NAME)) {
            throw UserExceptions.AccessDenied("change role");
        }
    }
}