package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "glamping")
@Table(name = "glamping")
public class GlampingEntity {
    //글램핑 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long glamp_id;//글램핑 ID
    private long user_id;//유저 ID
    private String glamp_name;//글램핑명
    private long star_point_avg;//평균 별점
    private String glamp_location;//글램핑 위치
    private String region;//글램핑 지역분류
    private String glamp_intro;//글램핑 소개
    private String info_basic;//글램핑 기본정보
    private String info_parking;//글램핑 주차정보
    private String info_notice;//글램핑 이용안내
    private String created_at;//글램핑 등록 일자


}
