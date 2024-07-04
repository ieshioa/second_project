package com.green.glampick.entity;

import com.green.glampick.dto.request.user.GetBookRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reservation")
@Table(name = "reservation")
public class ReservationEntity {
    //예약 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;//예약 ID
    @Column(nullable = false)
    private long userId;//유저 ID
    @Column(nullable = false)
    private long roomId;//객실 ID

    private String inputName;//예약자 성함
    @Column(nullable = false)
    private String checkInDate;//체크인 일자
    @Column(nullable = false)
    private String checkOutDate;//체크아웃 일자
    @Column(nullable = false)
    private long reservationAmount;//최종 결제 가격
    @Column(nullable = false)
    private long reservationStatus;//예약 상태
    @Column(nullable = false)
    private String comment;//예약 취소 사유
    @Column(nullable = false)
    private String createdAt;//예약 일자

    public ReservationEntity(GetBookRequestDto dto){
        this.reservationId = dto.getUserId();
    }



}
