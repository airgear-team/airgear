package com.airgear.integration;

import com.airgear.dto.GoodsDto;
import com.airgear.dto.UserDto;
import com.airgear.model.AuthToken;
import com.airgear.model.LoginUser;
import com.airgear.model.User;
import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserControllerTest {
    @LocalServerPort
    private int port;
    private static UserDto userTest;
    @Autowired
    private TestRestTemplate template;
    private static HttpHeaders headers;


    @BeforeAll
    public static void initTest() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        userTest = new UserDto();
        userTest.setName("UserTestUSER");
        userTest.setUsername("UserTestUSER");
        userTest.setPassword("1234");
    }

    @Test
    public void testAuthenticate() {
        LoginUser loginUser = new LoginUser(userTest.getUsername(), userTest.getPassword());
        HttpEntity<?> entity = new HttpEntity<>(loginUser, headers);
        ResponseEntity<AuthToken> response = this.template.exchange("http://localhost:" + port + "/auth/authenticate", HttpMethod.POST, entity, AuthToken.class);
        log.info("status response: " + response.getStatusCode());
        if (response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            UserDto user = template.postForObject("http://localhost:" + port + "/auth/register", new HttpEntity<>(userTest, headers), UserDto.class);
            if (user == null) {
                throw new RuntimeException("User don't create!");
            }

            log.info("user create!");
            response = template.exchange("http://localhost:" + port + "/auth/authenticate", HttpMethod.POST, entity, AuthToken.class);
        }

        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        headers.set("Authorization", "Bearer" + (response.getBody()).getToken());
        log.info("token: " + (response.getBody()).getToken());
    }

    @Test
    public void createGoods(){
        User user = userTest.getUserFromDto();
        user.setPhone("");
        GoodsDto goods = new GoodsDto("bolt", "description", BigDecimal.valueOf(100.0D), "location", userTest.getUserFromDto());
        HttpEntity<?> entity = new HttpEntity<>(goods, headers);
        GoodsDto goodsRes = template.postForObject("http://localhost:" + port + "/goods", entity, GoodsDto.class);
        Assertions.assertNotNull(goodsRes);
        AssertionsForClassTypes.assertThat(goodsRes.getName()).isEqualTo("bolt");
    }
}
