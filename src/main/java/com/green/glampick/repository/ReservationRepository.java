package com.green.glampick.repository;

import com.green.glampick.dto.request.user.GetBookRequestDto;
import com.green.glampick.dto.response.user.GetBookResponseDto;
import com.green.glampick.entity.ReservationEntity;
import com.green.glampick.repository.resultset.GetBookResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(
            value =
            "SELECT A.created_at AS createdAt " +
            ", A.reservation_id AS reservationId " +
            ", A.check_in_date AS checkInDate " +
            ", A.check_out_date AS checkOutDate " +
            ", C.glamp_name AS glampName " +
            ", B.room_name AS roomName " +
            ", B.check_in_time AS checkInTime " +
            ", B.check_out_time AS checkOutTime " +
            ", A.reservation_status AS reservationStatus " +
            "FROM reservation A " +
            "JOIN room B " +
            "ON A.room_id = B.room_id " +
            "JOIN glamping C " +
            "ON B.glamp_id = C.glamp_id ",
            nativeQuery = true
    )
    GetBookResponseDto getBook(GetBookRequestDto userId);
}