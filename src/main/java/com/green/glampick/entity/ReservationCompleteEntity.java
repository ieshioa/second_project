package com.green.glampick.entity;

import com.green.glampick.dto.request.book.postBookRequestDto;
import com.green.glampick.dto.request.user.PostReviewRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reservation_complete")
@Table(name = "reservation_complete")
public class ReservationCompleteEntity {

    //예약 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;//예약 ID

    private long userId;//유저 ID

    private String bookId;

    private long glampId;

    private long roomId;  //객실 ID

    private String inputName;//예약자 성함

    private int personnel;

    LocalDate checkInDate;//체크인 일자

    LocalDate checkOutDate;//체크아웃 일자

    private String pg;

    private long payAmount;//최종 결제 가격

    @CreationTimestamp @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public ReservationCompleteEntity(PostReviewRequestDto dto){
        this.reservationId = dto.getReservationId();
    }

    public ReservationCompleteEntity(Long userId, String bookId, Long glampId, Long roomId, String inputName,
                                     LocalDate checkInDate, LocalDate checkOutDate,
                                     long payAmount, String pg, LocalDateTime createdAt) {
        this.userId = userId;
        this.bookId = bookId;
        this.glampId = glampId;
        this.roomId = roomId;
        this.inputName = inputName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.payAmount = payAmount;
        this.pg = pg;
        this.createdAt = createdAt;
    }

}
