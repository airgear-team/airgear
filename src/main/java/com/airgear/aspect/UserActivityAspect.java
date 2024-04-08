package com.airgear.aspect;

import com.airgear.model.User;
import com.airgear.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Optional;

@Aspect
@Component
@AllArgsConstructor
public class UserActivityAspect {

    private final UserRepository userRepository;

    @Before("execution(* com.airgear.controller.*Controller.*(..))")
    public void logUserActivity() {
        String email = getCurrentUsername();
        OffsetDateTime currentTime = OffsetDateTime.now();

        Optional<User> userDB = userRepository.findByEmail(email);
        userDB.ifPresent(user -> {
            user.setLastActivity(currentTime);
            userRepository.save(user);
        });
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
