package com.airgear.service.impl;

import com.airgear.dto.TokenResponse;
import com.airgear.dto.UserDto;
import com.airgear.service.GoogleTokenHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation of {@link GoogleTokenHandler} for handling Google tokens.
 *
 * <p>This service interacts with Google's token validation endpoint to validate and retrieve information
 * from a Google token. The extracted information is then used to create a {@link UserDto}.</p>
 *
 * <p>Configuration properties are used for the validation URL and default password.</p>
 *
 * @author Oleksandr Tuleninov
 * @version 1.0
 */
@Slf4j
@Service
public class GoogleTokenHandlerImpl implements GoogleTokenHandler {

    @Value("${validation.google.url}")
    private String thirdPartyUrl;

    @Value("${service.default.password}")
    private String defaultPassword;

    private final ObjectMapper objectMapper;

    public GoogleTokenHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Executes the handling of a Google token.
     *
     * @param token The Google token to be handled.
     * @return User information extracted from the token.
     */
    @Override
    public UserDto execute(String token) {
        String tokenUrl = thirdPartyUrl + token;

        TokenResponse tokenResponse = getTokenResponse(tokenUrl);

        return new UserDto(
                tokenResponse.sub(),
                defaultPassword,
                tokenResponse.email(),
                null,
                null
        );
    }

    private TokenResponse getTokenResponse(String validationUrl) {
        HttpURLConnection connection = getHttpURLConnection(validationUrl);
        StringBuilder response = readFromConnection(connection);
        String tokenInfo = response.toString();

        return parseTokenInfo(tokenInfo);
    }

    private TokenResponse parseTokenInfo(String tokenInfo) {
        try {
            return objectMapper.readValue(tokenInfo, TokenResponse.class);
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
