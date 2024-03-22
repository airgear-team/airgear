package com.airgear.service.impl;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.airgear.config.AccountStatusConfig;
import com.airgear.dto.AccountStatusDto;
import com.airgear.dto.RoleDto;
import com.airgear.exception.ForbiddenException;
import com.airgear.exception.UserExceptions;
import com.airgear.model.goods.Goods;
import com.airgear.exception.UserUniquenessViolationException;
import com.airgear.model.AccountStatus;
import com.airgear.model.email.EmailMessage;
import com.airgear.repository.AccountStatusRepository;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.UserRepository;
import com.airgear.model.Role;
import com.airgear.model.User;
import com.airgear.dto.UserDto;
import com.airgear.security.CustomUserDetails;
import com.airgear.service.RoleService;
import com.airgear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
        return new CustomUserDetails(user);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public List<User> findActiveUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> user.getAccountStatus() != null && user.getAccountStatus().getStatusName().equals("ACTIVE"))
                .collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
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
        User user = findByUsername(username);
        Set<Role> roles = user.getRoles();
        roles.add(roleService.findByName("ADMIN"));
        user.setRoles(roles);
        return update(user);
    }

    @Override
    public User deleteRole(String username, String role) {
        User user = findByUsername(username);
        Set<Role> roles = user.getRoles();
        roles.remove(roleService.findByName("ADMIN"));
        if (roles.isEmpty()) {
            roles.add(roleService.findByName("USER"));
        }
        return update(user);
    }


    @Override
    public User apponintModerator(String username) {
        User user = userRepository.findByUsername(username);
        user.getRoles().add(roleService.findByName("MODERATOR"));
        return userRepository.save(user);
    }

    @Override
    public User removeModerator(String username) {
        User user = userRepository.findByUsername(username);
        user.getRoles().remove(roleService.findByName("MODERATOR"));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void markUserAsPotentiallyScam(Long userId, boolean isScam) {
        userRepository.updateIsPotentiallyScamStatus(userId, isScam);
    }

    @Override
    public int countNewUsersBetweenDates(OffsetDateTime start, OffsetDateTime end) {
        return userRepository.countByCreatedAtBetween(start, end);
    }

    @Override
    public Set<Goods> getFavoriteGoods(Authentication auth) {
        User user = this.findByUsername(auth.getName());
        return userRepository.getFavoriteGoodsByUser(user.getId());
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
    public int countDeletedUsersBetweenDates(OffsetDateTime start, OffsetDateTime end) {
        return userRepository.countByDeleteAtBetween(start, end);
    }

    @Override
    @Transactional
    public UserDto blockUser(Long userId) {
        User user = getUserById(userId);
        user.setAccountStatus(accountStatusRepository.findByStatusName(AccountStatusConfig.INACTIVE.name()));
        return UserDto.fromUser(user);
    }

    @Override
    @Transactional
    public UserDto unblockUser(Long userId) {
        User user = getUserById(userId);
        user.setAccountStatus(accountStatusRepository.findByStatusName(AccountStatusConfig.ACTIVE.name()));
        return UserDto.fromUser(user);
    }


    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserExceptions.userNotFound(userId));
    }

}