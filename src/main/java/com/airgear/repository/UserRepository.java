package com.airgear.repository;

import com.airgear.model.User;
import com.airgear.model.Goods;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isPotentiallyScam = :isScam WHERE u.id = :userId")
    void updateIsPotentiallyScamStatus(@Param("userId") Long userId, @Param("isScam") boolean isScam);

    @Query("Select fg FROM User user join  user.favoriteGoods fg where user.id = :userId")
    Set<Goods> getFavoriteGoodsByUser(Long userId);

}
