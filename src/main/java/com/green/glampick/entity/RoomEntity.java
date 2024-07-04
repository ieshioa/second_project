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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;  //객실 ID
    private long glampId;  //글램핑 ID
    private String roomName;  //객실명
    private long roomPrice;  //객실 가격
    private long roomNumPeople;  //객실 기준인원
    private long roomMaxPeople;  //객실 최대인원
    private String checkInTime;  //객실 체크인 시간
    private String checkOutTime;  //객실 체크아웃 시간
    private String createdAt;  //객실 등록 일자


}
