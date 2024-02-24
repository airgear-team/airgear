package com.airgear.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() throws Exception {
        return Dotenv.configure()
                .directory(".env")
                .ignoreIfMalformed() //
                .ignoreIfMissing()
                .load();
    }
}
