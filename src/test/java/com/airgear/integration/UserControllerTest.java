package com.airgear.integration;

import com.airgear.dto.*;
import com.airgear.model.AccountStatus;
import com.airgear.model.AuthToken;
import com.airgear.model.RentalCard;
import com.airgear.model.User;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.airgear.model.goods.Category;
import com.airgear.model.goods.Goods;
import com.airgear.model.location.Location;
import com.airgear.model.region.Region;
import com.airgear.repository.AccountStatusRepository;
import com.airgear.repository.RoleRepository;
import com.airgear.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;
    private static User userTest;
    private static User adminTest;
    private static User moderatorTest;
    private static HttpHeaders headersUser;
    private static HttpHeaders headersAdmin;
    private static HttpHeaders headersModerator;

    private static Goods goodsTest;
    private static RentalCard rentalCard;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountStatusRepository accountStatusRepository;
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
        AccountStatus acStatus = accountStatusRepository.findByStatusName("ACTIVE");
        userTest.setAccountStatus(acStatus);
        userTest.setRoles(Stream.of(roleRepository.findRoleByName("USER")).collect(Collectors.toSet()));
        adminTest.setAccountStatus(acStatus);
        adminTest.setRoles(Stream.of(roleRepository.findRoleByName("ADMIN")).collect(Collectors.toSet()));
        moderatorTest.setRoles(Stream.of(roleRepository.findRoleByName("MODERATOR")).collect(Collectors.toSet()));
        moderatorTest.setAccountStatus(acStatus);
        userAuthenticate(userTest, headersUser,true);
        userAuthenticate(adminTest, headersAdmin, false);
        userAuthenticate(moderatorTest, headersModerator, false);
    }

    private void userAuthenticate(User user, HttpHeaders currentHeaders, Boolean isRegister){
        LoginUserDto loginUser = new LoginUserDto(user.getUsername(), user.getPassword());
        HttpEntity<?> entity = new HttpEntity<>(loginUser, currentHeaders);
        ResponseEntity<AuthToken> response = this.template.exchange("http://localhost:" + port + "/auth/authenticate", HttpMethod.POST, entity, AuthToken.class);
        log.info("status response: " + response.getStatusCode());
        if (response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            if (isRegister) {
                user = template.postForObject("http://localhost:" + port + "/auth/register", entity, User.class);
                assertNotNull(user.getCreatedAt());
                assertNotNull(user.getAccountStatus());
                Assertions.assertEquals(1L, user.getAccountStatus().getId());
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
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody().getToken());
        currentHeaders.set("Authorization", "Bearer" + (response.getBody()).getToken());

        log.info("token "+user.getUsername()+":" + (response.getBody()).getToken());
    }

//    @Test
//    @Order(2)
//    public void createGoodsByUser(){
//        Location location = new Location();
//        location.setRegion(new Region());
//        location.setSettlement("Konstantinivka");
//        Category category = new Category();
//        category.setName("TOOLS_AND_EQUIPMENT");
//        GoodsDto goods = GoodsDto.builder()
//                .name("bolt")
//                .description("description")
//                .price(BigDecimal.valueOf(100.0D))
//                .location(LocationDto.fromLocation(location))
//                .category(CategoryDto.fromCategory(category))
//                .phoneNumber("+380984757533")
//                .user(UserDto.fromUser(userTest)).build();
//        HttpEntity<?> entity = new HttpEntity<>(goods, headersUser);
//        goodsTest = template.postForObject("http://localhost:" + port + "/goods", entity, Goods.class);
//        assertNotNull(goodsTest);
//        assertThat(goodsTest.getName()).isEqualTo("bolt");
//        assertThat(goodsTest.getLocation().getSettlement()).isEqualTo("Konstantinivka");
//        assertThat(goodsTest.getCategory().getName()).isEqualTo("TOOLS_AND_EQUIPMENT");
//        assertNotNull(goodsTest.getCreatedAt());
//        assertNotNull(goodsTest.getGoodsStatus());
//        Assertions.assertEquals(1L, goodsTest.getGoodsStatus().getId());
//    }

    @Test
    @Order(3)
    public void updateGoodsByModerator(){
        HttpEntity<?> entity = new HttpEntity<>(goodsTest, headersModerator);
        ResponseEntity<Goods> response = template.exchange("http://localhost:" + port + "/goods/"+goodsTest.getId(), HttpMethod.PUT, entity, Goods.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Order(4)
    public void updateGoodsByUser(){
        HttpEntity<?> entity = new HttpEntity<>(goodsTest, headersUser);
        OffsetDateTime time = OffsetDateTime.now();
        ResponseEntity<Goods> response = template.exchange("http://localhost:" + port + "/goods/"+goodsTest.getId(), HttpMethod.PUT, entity, Goods.class);
        goodsTest = response.getBody();
        assertNotNull(goodsTest);
        assertNotNull(goodsTest.getLastModified());
        Assertions.assertTrue(goodsTest.getLastModified().isAfter(time));
    }

    @Test
    @Order(5)
    public void getAllUsersByModerator(){
        HttpEntity<?> entity = new HttpEntity<>("", headersModerator);
        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/users/", HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Order(6)
    public void getAllUsersByAdmin(){
        HttpEntity<?> entity = new HttpEntity<>("", headersAdmin);
        ResponseEntity<Object> response= template.exchange("http://localhost:" + port + "/users/", HttpMethod.GET, entity,Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<User> res = (List<User>)response.getBody();
        assertNotNull(res);
        Assertions.assertTrue(res.size()>=3);
    }

    @Test
    @Order(7)
    public void getUserByUserNameByModerator(){
        HttpEntity<?> entity = new HttpEntity<>("", headersModerator);
        ResponseEntity<User> response = template.exchange("http://localhost:" + port + "/users/userTest", HttpMethod.GET, entity, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Order(8)
    public void getUserByUserNameByAdmin(){
        HttpEntity<?> entity = new HttpEntity<>(null, headersAdmin);
        ResponseEntity<User> response = template.exchange("http://localhost:" + port + "/users/userTest", HttpMethod.GET, entity, User.class);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUsername());
        assertThat(response.getBody().getUsername()).isEqualTo("userTest");
    }

    @Test
    @Order(9)
    public void createRentalCardByUser(){

        RentalCardDto rentalCardDto = RentalCardDto.builder()
                .lessorUsername("userTest")
                .renterUsername("moderatorTest")
                .firstDate(OffsetDateTime.parse("2024-02-24T00:00:00.937Z"))
                .lastDate(OffsetDateTime.parse("2024-02-27T00:00:00.937Z"))
                .description("description")
                .goodsId(goodsTest.getId())
                .rentalPrice(BigDecimal.valueOf(200))
                .fine(BigDecimal.valueOf(0.01))
                .duration(ChronoUnit.DAYS)
                .quantity(7L).build();

        HttpEntity<?> entity = new HttpEntity<>(rentalCardDto, headersUser);
        rentalCard = template.postForObject("http://localhost:" + port + "/rental", entity, RentalCard.class);
        assertNotNull(rentalCard);
        assertThat(rentalCard.getGoods().getName()).isEqualTo("bolt");
        assertEquals(7, ChronoUnit.DAYS.between(rentalCard.getFirstDate(), rentalCard.getLastDate()));
        assertNotNull(goodsTest.getCreatedAt());
    }
    @Test
    @Order(10)
    public void updateRentalCardByUser(){
        rentalCard.setDescription("Changed");
        HttpEntity<?> entity = new HttpEntity<>(RentalCardDto.getDtoFromRentalCard(rentalCard), headersUser);
        ResponseEntity<RentalCard> response = template.exchange("http://localhost:" + port + "/rental/"+rentalCard.getId(), HttpMethod.PUT, entity, RentalCard.class);
        rentalCard = response.getBody();
        assertNotNull(rentalCard);
        assertEquals("Changed", rentalCard.getDescription());
    }
    @Test
    @Order(11)
    public void deleteRentalCardByUser(){
        HttpEntity<?> entity = new HttpEntity<>(rentalCard, headersUser);
        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/rental/"+rentalCard.getId(), HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertNull(response.getBody());
    }



    @Test
    @Order(12)
    public void deleteGoodsByModerator(){
        HttpEntity<?> entity = new HttpEntity<>(goodsTest, headersModerator);
        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/goods/"+goodsTest.getId(), HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Order(13)
    public void deleteGoodsByUser(){
        HttpEntity<?> entity = new HttpEntity<>(goodsTest, headersUser);
        ResponseEntity<String> response = template.exchange("http://localhost:" + port + "/goods/"+goodsTest.getId(), HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertNull(response.getBody());
    }

    @Test
    @Order(14)
    public void createLocation(){
        String st ="settlement=Ivanivka&region_id=1";
        HttpEntity<?> entityLocation = new HttpEntity<>(null, headersUser);
        ResponseEntity<Location> locationResp= template.postForEntity("http://localhost:" + port + "/location/create"+"?"+st, entityLocation, Location.class);
        assertNotNull(locationResp.getBody());
        assertThat(locationResp.getBody().getSettlement()).isEqualTo("Ivanivka");
    }

    @Test
    @Order(15)
    public void getAmountOfNewGoodsByCategoryTest(){
        String st ="fromDate=2000-03-11T00:00:00.937Z&toDate=3000-03-11T00:00:00.937Z";
        HttpEntity<?> entity = new HttpEntity<>(null, headersUser);
        ResponseEntity<String> resp= template.exchange("http://localhost:" + port + "/goods/category/total/new"+"?"+st, HttpMethod.GET, entity, String.class);
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().contains(goodsTest.getCategory().getName()));
    }

}
