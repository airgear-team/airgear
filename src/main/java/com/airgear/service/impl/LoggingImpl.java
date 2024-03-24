package com.airgear.service.impl;

import com.airgear.service.Logging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code LoggingImpl} implementation of the {@link Logging} service interface.
 * Provides methods to log information about incoming HTTP requests and outgoing HTTP responses.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Slf4j
@Service
public class LoggingImpl implements Logging {

    /**
     * Log information about the incoming HTTP request.
     *
     * @param request the HttpServletRequest object representing the incoming request
     */
    @Override
    public void log(HttpServletRequest request) {
        String message = buildRequestMessage(request);

        log.info(message);
    }

    /**
     * Log information about the outgoing HTTP response.
     *
     * @param response the HttpServletResponse object representing the outgoing response
     */
    @Override
    public void log(HttpServletResponse response) {
        String message = buildResponseMessage(response);

        log.info(message);
    }

    private String buildRequestMessage(HttpServletRequest request) {
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        if (nickname.equals("anonymousUser")) nickname = "unauthorized request";
        String path = request.getServletPath();
        String httpMethod = request.getMethod();

        return String.format("""
                Request: nickname - '%s', path - '%s', httpMethod - '%s'.
                """, nickname, path, httpMethod);
    }

    private String buildResponseMessage(HttpServletResponse response) {
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        if (nickname.equals("anonymousUser")) nickname = "unauthorized request";
        String httpResponseCode = HttpStatus.valueOf(response.getStatus()).toString();

        return String.format("""
                Response: nickname - '%s', httpResponseCode - '%s'.
                """, nickname, httpResponseCode);
    }
}
