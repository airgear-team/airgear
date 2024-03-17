package com.airgear.security;

import com.airgear.config.AccountStatusConfig;
import com.airgear.model.AccountStatus;
import com.airgear.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final User source;

    public CustomUserDetails(User source) {
        super(source.getUsername(),
                source.getPassword(),
                source.getAccountStatus().getStatusName().equals(AccountStatusConfig.ACTIVE.name()),
                true,
                true,
                true,
                getAuthority(source));
        this.source = source;
    }

    private Collection<? extends GrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }
}
