package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "reservation_cancel")
@Table(name = "reservation_cancel")
public class ReservationCancelEntity {
    //예약 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;  //예약 ID

    private long glampId;  // 글램핑 ID

    private long userId;  //유저 ID

    private long roomId;  //객실 ID

    private String inputName;  //예약자 성함

    private String checkInDate;  //체크인 일자

    private String checkOutDate;  //체크아웃 일자

    private long reservationAmount;  //최종 결제 가격

    private String comment;  //예약 취소 사유

    private String createdAt;  //예약 일자

    public ReservationCancelEntity(long userId, long glampId, long roomId, String inputName, String checkInDate
            , String checkOutDate, long reservationAmount, String comment, String createdAt)
    {
        this.userId = userId;
        this.glampId = glampId;
        this.roomId = roomId;
        this.inputName = inputName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.reservationAmount = reservationAmount;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
