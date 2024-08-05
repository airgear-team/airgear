package com.airgear.security;

import com.airgear.model.Role;
import com.airgear.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.signing.key}")
    private String secret;

    @Value("${jwt.authorities.key}")
    public String authoritiesKey;

    private final UserServiceImpl userService;

    private final PasswordEncoder encoder;

    private final ObjectMapper objectMapper;

    public WebSecurityConfig(UserServiceImpl userService,
                             PasswordEncoder encoder,
                             ObjectMapper objectMapper) {
        this.userService = userService;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(getPermitAllUrls()).permitAll()
                .antMatchers(HttpMethod.GET, "/goods").permitAll()
                .antMatchers(HttpMethod.GET, "/images/{id:\\d+}/**").permitAll()
                .antMatchers(HttpMethod.POST, "/reviews").hasAnyRole(Role.USER.getValue())
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private String[] getPermitAllUrls() {
        List<String> permitAllUrls = new ArrayList<>();
        permitAllUrls.add("/v3/api-docs/**");
        permitAllUrls.add("/swagger-ui/**");
        permitAllUrls.add("/swagger-ui.html");
        permitAllUrls.add("/auth/authenticate");
        permitAllUrls.add("/auth/register");
        permitAllUrls.add("/auth/service");
        permitAllUrls.add("/goods/random");
        permitAllUrls.add("/goods/similar");
        permitAllUrls.add("/goods/filter");
        permitAllUrls.add("/category/image/**");
        permitAllUrls.add("/locations/**");
        return permitAllUrls.toArray(new String[0]);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        var filter = new JWTAuthenticationFilter(authenticationManager(), objectMapper);
        filter.setFilterProcessesUrl("/auth/authenticate");
        return filter;
    }

    private JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JWTAuthorizationFilter(authenticationManager(), secret, authoritiesKey);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
