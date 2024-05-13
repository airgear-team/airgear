package com.airgear.service.impl;

import com.airgear.service.Logging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class LoggingImpl implements Logging {

    @Override
    public void log(HttpServletRequest request) {
        String message = buildRequestMessage(request);

        log.info(message);
    }

    @Override
    public void log(HttpServletResponse response) {
        String message = buildResponseMessage(response);

        log.info(message);
    }

    private String buildRequestMessage(HttpServletRequest request) {
        String path = request.getServletPath();
        String httpMethod = request.getMethod();

        return String.format("""
                Request: nickname - '%s', path - '%s', httpMethod - '%s'.
                """, getNickname(), path, httpMethod);
    }

    private String buildResponseMessage(HttpServletResponse response) {
        String httpResponseCode = HttpStatus.valueOf(response.getStatus()).toString();

        return String.format("""
                Response: nickname - '%s', httpResponseCode - '%s'.
                """, getNickname(), httpResponseCode);
    }

    private String getNickname() {
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        if (nickname.equals("anonymousUser")) nickname = "unauthorized request";
        return nickname;
    }
}