package com.green.glampick.repository;

import com.green.glampick.entity.ReviewEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    boolean existsByUserId(long userId);
    boolean existsByGlampId(long glampId);
    boolean existsByReviewContent(String reviewContent);
    boolean existsByReviewStarPoint(long reviewStarPoint);
    boolean existsByCreatedAt(String createdAt);



}