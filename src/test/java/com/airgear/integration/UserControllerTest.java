package com.airgear.integration;

import com.airgear.dto.GoodsDto;
import com.airgear.model.AuthToken;
import com.airgear.model.LoginUser;
import com.airgear.model.User;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.airgear.model.goods.Goods;
import com.airgear.repository.RoleRepository;
import com.airgear.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @LocalServerPort
    private int port;
    private static User userTest;
    private static User adminTest;
    private static User moderatorTest;
    @Autowired
    private TestRestTemplate template;
    private static HttpHeaders headersUser;
    private static HttpHeaders headersAdmin;
    private static HttpHeaders headersModerator;

    private static Goods goodsTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @BeforeAll
    public static void initTest() {
        headersUser = new HttpHeaders();
        headersUser.setContentType(MediaType.APPLICATION_JSON);

        headersAdmin = new HttpHeaders();
        headersAdmin.setContentType(MediaType.APPLICATION_JSON);

        headersModerator = new HttpHeaders();
        headersModerator.setContentType(MediaType.APPLICATION_JSON);

        userTest = new User();
        userTest.setUsername("userTest");
        userTest.setPassword("1111");
        userTest.setCreatedAt(OffsetDateTime.now());

        adminTest = new User();
        adminTest.setUsername("adminTest");
        adminTest.setPassword("2222");
        adminTest.setCreatedAt(OffsetDateTime.now());

        moderatorTest = new User();
        moderatorTest.setUsername("moderatorTest");
        moderatorTest.setPassword("3333");
        moderatorTest.setCreatedAt(OffsetDateTime.now());
    }


    @Test
    @Order(1)
    public void testAuthenticate() {
        adminTest.setRoles(Stream.of(roleRepository.findRoleByName("ADMIN")).collect(Collectors.toSet()));
        moderatorTest.setRoles(Stream.of(roleRepository.findRoleByName("MODERATOR")).collect(Collectors.toSet()));
        userAuthenticate(userTest, headersUser,true);
        userAuthenticate(adminTest, headersAdmin, false);
        userAuthenticate(moderatorTest, headersModerator, false);
    }
    private void userAuthenticate(User user, HttpHeaders currentHeaders, Boolean isRegister){
        LoginUser loginUser = new LoginUser(user.getUsername(), user.getPassword());
        HttpEntity<?> entity = new HttpEntity<>(loginUser, currentHeaders);
        ResponseEntity<AuthToken> response = this.template.exchange("http://localhost:" + port + "/auth/authenticate", HttpMethod.POST, entity, AuthToken.class);
        log.info("status response: " + response.getStatusCode());
        if (response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            if (isRegister) {
                user = template.postForObject("http://localhost:" + port + "/auth/register", entity, User.class);
            }else{
                user.setPassword(bcryptEncoder.encode(user.getPassword()));
                user =userRepository.save(user);
            }
            if (user == null) {
                throw new RuntimeException("User: "+user.getUsername()+" don't create!");
            }
            log.info("User: "+user.getUsername()+" create!");
        }
        response = template.exchange("http://localhost:" + port + "/auth/authenticate", HttpMethod.POST, entity, AuthToken.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        currentHeaders.set("Authorization", "Bearer" + (response.getBody()).getToken());
        log.info("token "+user.getUsername()+":" + (response.getBody()).getToken());
    }


    @Test
    @Order(2)
    public void createGoodsByUser(){
        GoodsDto goods = new GoodsDto("bolt", "description", BigDecimal.valueOf(100.0D), "location", userTest);
        HttpEntity<?> entity = new HttpEntity<>(goods, headersUser);
        goodsTest = template.postForObject("http://localhost:" + port + "/goods", entity, Goods.class);
        Assertions.assertNotNull(goodsTest);
        AssertionsForClassTypes.assertThat(goodsTest.getName()).isEqualTo("bolt");
    }

    @Test
    @Order(3)
    public void updateGoodsByModerator(){
        HttpEntity<?> entity = new HttpEntity<>(goodsTest, headersModerator);
        ResponseEntity<Goods> response = template.exchange("http://localhost:" + port + "/goods/"+goodsTest.getId(), HttpMethod.PUT, entity, Goods.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Order(4)
    public void updateGoodsByUser(){
        HttpEntity<?> entity = new HttpEntity<>(goodsTest, headersUser);
        ResponseEntity<Goods> response = template.exchange("http://localhost:" + port + "/goods/"+goodsTest.getId(), HttpMethod.PUT, entity, Goods.class);
        goodsTest = response.getBody();
        Assertions.assertNotNull(goodsTest);
        Assertions.assertNotNull(goodsTest.getLastModified());
    }

    @Test
    @Order(5)
    public void deleteGoodsByModerator(){
        HttpEntity<?> entity = new HttpEntity<>(goodsTest, headersModerator);
        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/goods/"+goodsTest.getId(), HttpMethod.DELETE, entity, String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Order(6)
    public void deleteGoodsByUser(){
        HttpEntity<?> entity = new HttpEntity<>(goodsTest, headersUser);
        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/goods/"+goodsTest.getId(), HttpMethod.DELETE, entity, String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertNull(response.getBody());
    }


}
