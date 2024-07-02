package com.green.glampick.entity;

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
    private long reservation_id;//예약 ID
    private long user_id;//유저 ID
    private long room_id;//객실 ID
    private String input_name;//예약자 성함
    private String check_in_date;//체크인 일자
    private String check_out_date;//체크아웃 일자
    private long reservation_amount;//최종 결제 가격
    private long reservation_status;//예약 상태
    private String comment;//예약 취소 사유
    private String created_at;//예약 일자


}
