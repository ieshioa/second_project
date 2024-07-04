package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "service")
@Table(name = "service")
public class ServiceEntity {
    //서비스시설 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceId;  //서비스 ID
    private String serviceTitle;  //서비스 이름
    private String createdAt;  //데이터 등록 일자


}
