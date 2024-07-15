package com.green.glampick.entity;

import com.green.glampick.dto.request.user.PostAvgRequest;
import com.green.glampick.dto.request.user.PostReviewRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "glamping")
@Table(name = "glamping")
public class GlampingEntity{
    //글램핑 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long glampId;  //글램핑 ID
    private long userId;  //유저 ID
    private String glampName;  //글램핑명
    private long starPointAvg;  //평균 별점
    private long reviewCount; // 댓글갯수
    private String glampLocation;  //글램핑 위치
    private String region;  //글램핑 지역분류
    private String glampIntro;  //글램핑 소개
    private String infoBasic;  //글램핑 기본정보
    private String infoParking;  //글램핑 주차정보
    private String infoNotice;  //글램핑 이용안내
    private String createdAt;  //글램핑 등록 일자


    public GlampingEntity(PostAvgRequest dto) {

        this.userId = dto.getUserId();
        this.glampId = dto.getGlampId();
        this.starPointAvg = dto.getStarPointAvg();
        this.reviewCount = dto.getReviewCount();

    }
}