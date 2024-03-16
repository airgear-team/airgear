package com.airgear.data.generator;
import com.airgear.dto.UserDto;
import com.airgear.model.goods.Goods;
import com.airgear.model.User;
import com.airgear.model.goods.Location;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.util.Random;

public class GenerateDataService implements CommandLineRunner {
    private static final int MIN_NUMBER_OF_GOODS = 3;
    private static final int MAX_NUMBER_OF_GOODS = 6;
    private static final int NUMBER_OF_USERS = 10;

    private UserService userService;

    private GoodsService goodsService;

    private GoodsRepository goodsRepository;

    private UserRepository userRepository;

    private final Random random = new Random();

    /**
     * Entry point for the Spring Boot application to run this service.
     * It clears existing users and goods from the database and then generates new data.
     * @param args Command line arguments (not used).
     */
    @Override
    public void run(String... args) {
        clearUsersAndGoods();
        generateUsersAndGoods(NUMBER_OF_USERS);
    }

    private void clearUsersAndGoods() {
        goodsRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Generates a specified number of users and a random number of goods for each user.
     * @param numberOfUsers The number of users to generate.
     */
    public void generateUsersAndGoods(int numberOfUsers) {
        for (int i = 0; i < numberOfUsers; i++) {
            User savedUser = generateUserById(i);
            int numberOfGoods = random.nextInt((MAX_NUMBER_OF_GOODS - MIN_NUMBER_OF_GOODS) + 1) + MIN_NUMBER_OF_GOODS;
            generateGoodsForUser(savedUser, numberOfGoods);
        }
    }

    /**
     * Generates and saves a user based on a given identifier.
     * @param i The identifier used to generate unique user details.
     * @return The saved user.
     */
    private User generateUserById(int i) {
        UserDto userDto = UserDto.builder()
                .username("username" + i)
                .password("Password" + i)
                .email("user" + i + "@gmail.com")
                .phone(generateUniquePhoneNumber())
                .name("user name " + i).build();

        return userService.save(userDto);
    }

    /**
     * Generates and saves a specified number of goods for a given user.
     * @param user The user for whom to generate goods.
     * @param numberOfGoods The number of goods to generate for the user.
     */
    private void generateGoodsForUser(User user, int numberOfGoods) {
        for (int i = 0; i < numberOfGoods; i++) {
            Goods goods = new Goods();
            goods.setName("Goods" + user.getId() + "_" + i);
            goods.setDescription("Description for Goods " + user.getId() + "_" + i);
            goods.setPrice(new BigDecimal("100.00").multiply(new BigDecimal(i + 1)));
            goods.setLocation(new Location());
            goods.setUser(user);
            goodsService.saveGoods(goods);
        }
    }

    private String generateUniquePhoneNumber() {
        return "555" + (1000 + random.nextInt(9000));
    }
}
