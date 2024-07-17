package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String bookId;

    private long roomId;  //객실 ID

    private String inputName;  //예약자 성함

    private int personnel;

    private LocalDate checkInDate;  //체크인 일자

    private LocalDate checkOutDate;  //체크아웃 일자

    private String pg;

    private long payAmount;  //최종 결제 가격

    private String comment;  //예약 취소 사유

    @CreationTimestamp @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ReservationCancelEntity(long reservationId, long userId, String bookId, long glampId, long roomId, String inputName,
                                   int personnel, LocalDate checkInDate, LocalDate checkOutDate, String pg,
                                   long payAmount, String comment, LocalDateTime createdAt)
    {
        this.reservationId = reservationId;
        this.userId = userId;
        this.bookId = bookId;
        this.glampId = glampId;
        this.roomId = roomId;
        this.inputName = inputName;
        this.personnel = personnel;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.pg = pg;
        this.payAmount = payAmount;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
