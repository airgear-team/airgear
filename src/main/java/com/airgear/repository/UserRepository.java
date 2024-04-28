package com.airgear.repository;

import com.airgear.model.Goods;
import com.airgear.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("Select fg FROM User user join  user.favoriteGoods fg where user.id = :userId")
    Set<Goods> getFavoriteGoodsByUser(Long userId);

}
