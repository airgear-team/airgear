package com.airgear.security;

import com.airgear.model.User;
import com.airgear.model.UserStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    public CustomUserDetails(User source) {
        super(source.getEmail(),
                source.getPassword(),
                source.getStatus().equals(UserStatus.ACTIVE),
                true,
                true,
                true,
                getAuthorities(source));
    }

    private static Set<SimpleGrantedAuthority> getAuthorities(User source) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        source.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
        });
        return authorities;
    }

}