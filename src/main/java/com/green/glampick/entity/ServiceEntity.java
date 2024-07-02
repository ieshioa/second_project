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
    private long service_id;//서비스 ID
    private String service_title; //서비스 이름
    private String created_at;//데이터 등록 일자


}
