package com.airgear.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class PasswordEncoderConfig {
    
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

}