package com.airgear.service.impl;

import com.airgear.dto.GoodsCreateRequest;
import com.airgear.dto.SaveUserRequestDto;
import com.airgear.dto.UserDto;
import com.airgear.dto.UserExistDto;
import com.airgear.exception.UserExceptions;
import com.airgear.mapper.GoodsMapper;
import com.airgear.mapper.UserMapper;
import com.airgear.model.Role;
import com.airgear.model.User;
import com.airgear.model.UserStatus;
import com.airgear.entity.EmailMessage;
import com.airgear.repository.UserRepository;
import com.airgear.model.CustomUserDetails;
import com.airgear.service.EmailService;
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

import static com.airgear.utils.Constants.ROLE_ADMIN_NAME;

@Service(value = "userService")
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final GoodsMapper goodsMapper;
    private final EmailService emailService;

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

    private void sendFarewellEmail(Set<String> userEmails) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject("We're sad to see you go!");
        emailMessage.setMessage("Hello,\n\nWe noticed that your account is now inactive. We're sorry to see you leave! If there was any issue with our service, or if you have any feedback, please let us know. We hope to serve you again in the future.\n\nBest,\nThe Airgear Team");

        emailService.sendMail(emailMessage, userEmails);
    }

    @Override
    public void setAccountStatus(String email, UserStatus status) {
        User user = getUser(email);
        if (user == null || user.getStatus().equals(status)) {
            throw UserExceptions.userNotFound(email);
        }

        user.setStatus(status);
        userRepository.save(user);

        if (status.equals(UserStatus.SUSPENDED)) {
            sendFarewellEmail(Set.of(user.getEmail()));
        }
    }

    @Override
    public UserDto create(SaveUserRequestDto request) {
        validateUniqueFields(request);
        User user = save(request);
        return userMapper.toDto(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto appointRole(String email, Role role) {
        User user = getUser(email);
        user.getRoles().add(role);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeRole(String email, Role role) {
        User user = getUser(email);
        user.getRoles().remove(role);
        return userMapper.toDto(user);
    }

    @Override
    public User addRole(String email, String role) {
        User user = getUser(email);
        Set<Role> roles = user.getRoles();
        roles.add(Role.ADMIN);
        user.setRoles(roles);
        return update(user);
    }

    @Override
    public User deleteRole(String email, String role) {
        User user = getUser(email);
        Set<Role> roles = user.getRoles();
        roles.remove(Role.ADMIN);
        if (roles.isEmpty()) {
            roles.add(Role.USER);
        }
        return update(user);
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
    public void checkForUserUniqueness(UserDto userDto){
        boolean emailExists = userRepository.existsByEmail(userDto.getEmail());
        boolean phoneExists = userRepository.existsByPhone(userDto.getPhone());

        if (emailExists) {
            throw UserExceptions.duplicateEmail(userDto.getEmail());
        }
        if (phoneExists) {
            throw UserExceptions.duplicatePhone(userDto.getPhone());
        }
    }

    @Override
    public UserDto blockUser(Long userId) {
        User user = getUserById(userId);
        user.setStatus(UserStatus.SUSPENDED);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto unblockUser(Long userId) {
        User user = getUserById(userId);
        user.setStatus(UserStatus.ACTIVE);
        return userMapper.toDto(user);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserExceptions.userNotFound(userId));
    }

    @Override
    public void deleteAccount(String email) {
        User user = getUser(email);
        user.setStatus(UserStatus.SUSPENDED);
    }

    @Override
    public void accessToRoleChange(String executor, Role role) {
        User executorUser = getUser(executor);
        if (!executorUser.getRoles().contains(role) && role.toString().equalsIgnoreCase(ROLE_ADMIN_NAME)) {
            throw UserExceptions.AccessDenied("change role");
        }
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
