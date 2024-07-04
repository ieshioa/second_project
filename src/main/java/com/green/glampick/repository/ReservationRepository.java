package com.green.glampick.repository;

import com.green.glampick.entity.ReservationEntity;
import com.green.glampick.repository.resultset.GetBookResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(
            value =
            "SELECT C.glamp_name AS glampName" +
            ", B.room_name AS roomName" +
            ", A. check_in_date AS checkInDate" +
            ", A. check_out_date AS checkOutDate " +
            ", A. created_at AS createdAt " +
            "FROM reservation_before A " +
            "JOIN room B " +
            "ON A.room_id = B.room_id " +
            "JOIN glamping C " +
            "ON B.glamp_id = C.glamp_id " +
            "WHERE A.user_id = :userId " +
            "ORDER BY A.created_at DESC ",
            nativeQuery = true
    )
    GetBookResultSet getBook(Long userId);
}
