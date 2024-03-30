package com.airgear.service.impl;

import com.airgear.dto.LoginUserDto;
import com.airgear.dto.TokenResponseDTO;
import com.airgear.dto.UserDto;
import com.airgear.service.GoogleTokenHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleTokenHandlerImpl implements GoogleTokenHandler {

    @Value("${validation.google.url}")
    private String thirdPartyUrl;

    @Value("${service.default.password}")
    private String defaultPassword;

    private final ObjectMapper objectMapper;

    @Override
    public LoginUserDto execute(String token) {
        String tokenUrl = thirdPartyUrl + token;

        TokenResponseDTO tokenResponse = getTokenResponse(tokenUrl);

        return new LoginUserDto(
                tokenResponse.getSub(),
                defaultPassword);
    }

    private TokenResponseDTO getTokenResponse(String validationUrl) {
        HttpURLConnection connection = getHttpURLConnection(validationUrl);
        StringBuilder response = readFromConnection(connection);
        String tokenInfo = response.toString();

        return parseTokenInfo(tokenInfo);
    }

    private TokenResponseDTO parseTokenInfo(String tokenInfo) {
        try {
            return objectMapper.readValue(tokenInfo, TokenResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException in 'getTokenRequest(String tokenInfo)' method.");
            throw new RuntimeException(e);
        }
    }

    private HttpURLConnection getHttpURLConnection(String validationUrl) {
        HttpURLConnection connection;
        try {
            URL url = new URL(validationUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            log.error("IOException in 'getHttpURLConnection(String validationUrl)' method.");
            throw new UncheckedIOException(e);
        }

        return connection;
    }

    private StringBuilder readFromConnection(HttpURLConnection connection) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            log.error("IOException in 'readFromConnection(HttpURLConnection connection)' method.");
            throw new UncheckedIOException(e);
        }

        return response;
    }
}
