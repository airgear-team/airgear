package com.airgear.aspect;

import com.airgear.model.User;
import com.airgear.repository.UserRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Aspect
@Component
public class UserActivityAspect {

    private final UserRepository userRepository;

    @Autowired
    public UserActivityAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("execution(* com.airgear.controller.*Controller.*(..))")
    public void logUserActivity() {
        String username = getCurrentUsername();
        OffsetDateTime currentTime = OffsetDateTime.now();

        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setLastActivity(currentTime);
            userRepository.save(user);
        }
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
