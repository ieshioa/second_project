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

    boolean existsByUserId(long userId);

    boolean existsByGlampId(long glampId);

    boolean existsByReviewContent(String reviewContent);

    boolean existsByReviewStarPoint(long reviewStarPoint);

    boolean existsByCreatedAt(String createdAt);

    ReviewEntity findByReviewId(long reviewId);


    @Query(
            value =
            "SELECT A.review_star_point AS reviewStarPoint " +
            ", A.created_at AS createdAt " +
            ", B.user_nickname AS userNickname " +
            ", C.glamp_name AS glampName " +
            ", D.room_name AS roomName " +
            ", A.review_content AS reviewContent " +
            ", A.review_comment AS reviewComment " +
            ", B.user_profile_image AS userProfileImage " +
            "FROM review A " +
            "JOIN user B " +
            "ON A.user_id = B.user_id " +
            "JOIN glamping C " +
            "ON A.glamp_id = C.glamp_id " +
            "JOIN room D " +
            "ON A.room_id = D.room_id " +
            "WHERE A.user_id = :userId  " +
            "ORDER BY A.created_at DESC ",
            nativeQuery = true
    )
    List<GetUserReviewResultSet> getReview(Long userId);
}