package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "room")
@Table(name = "room")
public class RoomEntity {
    //객실 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long room_id;//객실 ID
    private long glamp_id;//글램핑 ID
    private String room_name;//객실명
    private long room_price;//객실 가격
    private long room_num_people;//객실 기준인원
    private long room_max_people;//객실 최대인원
    private String check_in_time;//객실 체크인 시간
    private String check_out_time;//객실 체크아웃 시간
    private String created_at;//객실 등록 일자


}
