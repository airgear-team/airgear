package com.airgear.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private final String secret;
    private final String authoritiesKey;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  String secret, String authoritiesKey) {
        super(authenticationManager);
        this.secret = secret;
        this.authoritiesKey = authoritiesKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        var securityContext = SecurityContextHolder.getContext();

        var authentication = securityContext.getAuthentication();
        // if authenticated by other means, such as JWTAuthenticationFilter
        if (authentication != null && authentication.isAuthenticated()) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(SecurityConstants.AUTH_TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String encodedJwt = header.substring(SecurityConstants.AUTH_TOKEN_PREFIX.length());
        authentication = getAuthentication(encodedJwt);

        securityContext.setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String encodedJwt) {
        // parse the token
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(encodedJwt)
                    .getBody();
        } catch (Exception e) {
            log.debug("Invalid JWT received", e);
            return null;
        }

        String username = claims.getSubject();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(authoritiesKey).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}

