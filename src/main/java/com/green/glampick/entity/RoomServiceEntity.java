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
    private long room_id;//객실 ID
    private long service_id;//서비스 ID
    private String created_at;//객실 서비스 등록 일자

}
