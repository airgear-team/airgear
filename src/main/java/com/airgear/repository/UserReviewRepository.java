package com.airgear.repository;

import com.airgear.model.User;
import com.airgear.model.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
    List<UserReview> findByReviewedUser(User user);

    @Query(value = "SELECT AVG(rating) FROM user_reviews WHERE reviewed_user_id = :userId", nativeQuery = true)
    Float calculateAverageRatingForUser(@Param("userId") Long userId);

    @Query("SELECT count(id)  FROM UserReview userReview  WHERE userReview.reviewer = :reviewer AND userReview.reviewedUser = :reviewedUser")
    Long countByReviewedAndByReviewer(User reviewedUser, User reviewer);


}
