package com.airgear.repository;

import com.airgear.model.User;
import com.airgear.model.goods.Goods;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import java.time.OffsetDateTime;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET account_status_id = :accountStatusId WHERE id = :userId", nativeQuery = true)
    int setAccountStatusId(@Param("accountStatusId") long accountStatusId, @Param("userId") long userId);

    @Query("SELECT u.username AS username, COUNT(g) AS goodsCount " +
            "FROM User u JOIN u.goods g " +
            "GROUP BY u.username")
    List<Map<String, Integer>> findUserGoodsCount(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isPotentiallyScam = :isScam WHERE u.id = :userId")
    void updateIsPotentiallyScamStatus(@Param("userId") Long userId, @Param("isScam") boolean isScam);

    int countByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);

    @Query("Select fg FROM User user join  user.favoriteGoods fg where user.id = :userId")
    Set<Goods> getFavoriteGoodsByUser(Long userId);
}