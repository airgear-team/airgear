package com.airgear.security;

import com.airgear.config.AccountStatusConfig;
import com.airgear.model.AccountStatus;
import com.airgear.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    public CustomUserDetails(User source) {
        super(source.getUsername(),
                source.getPassword(),
                source.getAccountStatus().getStatusName().equals(AccountStatusConfig.ACTIVE.name()),
                true,
                true,
                true,
                getAuthorities(source));
    }

    private static Set<SimpleGrantedAuthority> getAuthorities(User source) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        source.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

}