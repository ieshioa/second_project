package com.green.glampick.repository;

import com.green.glampick.entity.ReviewEntity;
import com.green.glampick.repository.resultset.GetUserReviewResultSet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {


    @Query (
        value =
        "SELECT E.glamp_name AS glampName " +
        ", D.room_name AS roomName " +
        ", E.glamp_id AS glampId " +
        ", C.user_nickname AS userNickname " +
        ", C.user_profile_image AS userProfileImage " +
        ", B.review_id AS reviewId " +
        ", B.reservation_id AS reservationId " +
        ", B.review_content AS reviewContent " +
        ", B.review_star_point AS reviewStarPoint " +
        ", B.review_comment AS ownerReviewComment " +
        ", B.created_at AS createdAt " +
        ", A.book_id AS bookId " +
        "FROM reservation_complete A " +
        "JOIN review B " +
        "ON A.reservation_id = B.reservation_id " +
        "JOIN user C " +
        "ON C.user_id = B.user_id " +
        "JOIN room D " +
        "ON A.room_id = D.room_id " +
        "JOIN glamping E " +
        "ON A.glamp_id = E.glamp_id " +
        "WHERE C.user_id = ?1 " +
        "ORDER BY B.review_id DESC " +
        "LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    List<GetUserReviewResultSet> getReview(long userId, int limit, int offset);

    @Query(
            value = "SELECT COUNT(*) FROM reservation_complete A " +
                    "JOIN review B ON A.reservation_id = B.reservation_id " +
                    "JOIN user C ON C.user_id = B.user_id " +
                    "JOIN room D ON A.room_id = D.room_id " +
                    "JOIN glamping E ON A.glamp_id = E.glamp_id " +
                    "WHERE C.user_id = ?1",
            nativeQuery = true
    )
    long getTotalReviewsCount(Long userId);


//    UPDATE glamping
//    SET review_count = (
//            SELECT COUNT(review_content)
//    FROM review
//    WHERE glamp_id = 3
//)
//    WHERE user_id = 11
//    AND glamp_id = 3;
//    SELECT *
//    FROM glamping;
}