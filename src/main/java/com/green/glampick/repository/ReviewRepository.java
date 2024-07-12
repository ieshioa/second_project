package com.green.glampick.repository;

import com.green.glampick.entity.ReviewEntity;
import com.green.glampick.repository.resultset.GetUserReviewResultSet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {


    @Query (
        value =
        "SELECT C.glamp_name AS glampName " +
        ", D.room_name AS roomName " +
        ", B.user_nickname AS userNickname" +
        ", B.user_profile_image AS userProfileImage " +
        ", A.review_id AS reviewId " +
        ", A.review_content AS reviewContent " +
        ", A.review_star_point AS reviewStarPoint " +
        ", A.review_comment AS ownerReviewComment " +
        ", A.created_at AS createdAt " +
        "FROM review A " +
        "JOIN user B ON A.user_id = B.user_id " +
        "JOIN glamping C ON A.glamp_id = C.glamp_id " +
        "JOIN room D ON A.room_id = D.room_id " +
        "WHERE B.user_id = ?1 " +
        "ORDER BY A.created_at DESC " +
        "LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    List<GetUserReviewResultSet> getReview(long userId, int limit, int offset);

}