package com.green.glampick.repository;

import com.green.glampick.entity.ReservationCancelEntity;
import com.green.glampick.repository.resultset.GetReservationCancelResultSet;
import com.green.glampick.repository.resultset.GetReservationCompleteResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationCancelRepository extends JpaRepository<ReservationCancelEntity, Long> {

    @Query(
            value =
                    "SELECT C. glamp_name AS glampName" +
                            ", C.glamp_id AS glampId " +
                            ", C.glamp_image AS glampImage " +
                            ", A.book_id AS bookId " +
                            ", B.room_name AS roomName " +
                            ", A. reservation_id AS reservationId" +
                            ", A. check_in_date AS checkInDate " +
                            ", A. check_out_date AS checkOutDate " +
                            ", A. comment AS comment " +
                            ", A. created_at AS createdAt " +
                            ", B. check_in_time AS checkInTime " +
                            ", B. check_out_time AS checkOutTime " +
                            "FROM reservation_cancel A " +
                            "JOIN room B " +
                            "ON A.room_id = B.room_id " +
                            "JOIN glamping C " +
                            "ON B.glamp_id = C.glamp_id " +
                            "WHERE A.user_id = :userId " +
                            "ORDER BY A.check_in_date DESC ",
            nativeQuery = true
    )
    List<GetReservationCancelResultSet> getBook(Long userId);

}
