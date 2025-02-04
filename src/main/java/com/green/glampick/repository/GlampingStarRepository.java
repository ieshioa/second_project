package com.green.glampick.repository;


import com.green.glampick.entity.GlampingEntity;
import com.green.glampick.entity.ReviewEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlampingStarRepository extends JpaRepository<ReviewEntity, Long> {


//    @Modifying
    @Transactional
    @Query(
            value =
            "UPDATE glamping " +
            "SET star_point_avg = (" +
            "SELECT TRUNCATE(AVG(review_star_point),1) " +
            "FROM review WHERE glamp_id = :glampId) " +
            ", review_count = (SELECT COUNT(review_content) " +
            "FROM review WHERE glamp_id = :glampId) " +
            "WHERE glamp_id = :glampId ",

            nativeQuery = true)
    void findStarPointAvg(long glampId);


//    @Modifying
    @Transactional
    @Query(
            value =
            "UPDATE review B " +
            "JOIN reservation_complete A " +
            "ON A.reservation_id = B.reservation_id " +
            "SET B.glamp_id = A.glamp_id , A.status = 1 " +
            "WHERE B.reservation_id = :reservationId ",
            nativeQuery = true)
    void fin(long reservationId);
}
