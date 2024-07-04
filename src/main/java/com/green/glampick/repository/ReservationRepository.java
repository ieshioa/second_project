package com.green.glampick.repository;

// import com.green.glampick.entity.ReservationEntity;
// import com.green.glampick.repository.resultset.GetBookResultSet;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;

//@Repository
//public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
//
////    @Query(
////            value =
////                    SELECT A.created_at
////            , A.reservation_id
////            , A.check_in_date
////            , A.check_out_date
////            , C.glamp_name
////            , B.room_name
////            , B.check_in_time
////            , B.check_out_time
////            FROM reservation A
////            JOIN room B
////            ON A.room_id = B.room_id
////            JOIN glamping C
////            ON B.glamp_id = C.glamp_id
////            nativeQuery = true
////    )
//    GetBookResultSet getBook(Long reservationId);
//}
