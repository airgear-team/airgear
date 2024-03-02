package com.airgear.integration;

import com.airgear.dto.GoodsDto;
import com.airgear.model.AuthToken;
import com.airgear.model.User;
import com.airgear.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    UserService userService;

    private static JSONObject userJSON;

    @Autowired
    private TestRestTemplate template;
    private static HttpHeaders headers;

    @BeforeAll
    public static void initTest() throws JSONException {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        userJSON = new JSONObject();
        userJSON.put("name","UserTestUSER");
        userJSON.put("username","UserTestUSER");
        userJSON.put("password","1234");
    }

    @Test
    public void testAuthenticate(){
        HttpEntity<?> entity = new HttpEntity<>(userJSON.toString(), headers);
        User user = userService.findByUsername("UserTestUSER");
        if (user==null){
            user = template.postForObject("http://localhost:"+port+"/auth/register",entity, User.class);
            if(user ==null){
                throw new RuntimeException("Не создался пользователь!");
            }
            else System.out.println("Пользователь создан!");
        }
        ResponseEntity<AuthToken> response = template.exchange("http://localhost:"+8888+"/auth/authenticate", HttpMethod.POST,
                         entity, AuthToken.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        headers.set("Authorization","Bearer"+response.getBody().getToken());
    }

    @Test
    public void createGoods() throws JSONException {
        JSONObject goodsJSON = new JSONObject();
        goodsJSON.put("name","bolt222");
        goodsJSON.put("price",100.00);
        goodsJSON.put("description","gggggggggggggg");
        goodsJSON.put("location","nnnnnnnnnnnnn");
        goodsJSON.put("user",userJSON);
        HttpEntity<?> entity = new HttpEntity<>(goodsJSON.toString(), headers);
        GoodsDto goodsRes = template.postForObject("http://localhost:"+port+"/goods",entity, GoodsDto.class);
        assertNotNull(goodsRes);
        assertThat(goodsRes.getName()).isEqualTo("bolt222");
    }

}
