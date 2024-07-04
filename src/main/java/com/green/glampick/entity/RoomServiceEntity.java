package com.green.glampick.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "room_service")
@Table(name = "room_service")
public class RoomServiceEntity {
    //룸 서비스시설 테이블
    @Id
    private long roomId;  //객실 ID
    private long serviceId;  //서비스 ID
    private String createdAt;  //객실 서비스 등록 일자

}
