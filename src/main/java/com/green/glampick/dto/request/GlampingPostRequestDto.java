package com.green.glampick.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.glampick.dto.object.glamping.RoomItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GlampingPostRequestDto {
    // glamping 테이블
    @JsonIgnore
    private long userId;
    @Schema(example = "뉴욕 카라반", description = "글램핑 이름")
    private String glampName;
    @Schema(example = "대구광역시 중구 109-2", description = "글램핑 주소")
    private String glampLocation;
    @Schema(example = "서울경기", description = "지역 분류")
    private String region;
    @Schema(example = "10000", description = "추가 인원에 대한 추가 요금")
    private int extraCharge;
    @Schema(example = "소개소개", description = "글램핑 소개")
    private String intro;
    @Schema(example = "글램핑입니다", description = "기본 정보")
    private String basic;
    @Schema(example = "주차장 있어요", description = "주차 정보")
    private String parking;
    @Schema(example = "이거 저거 주의해주세요", description = "이용 안내")
    private String notice;

    // 이미지
    @JsonIgnore
    private String glampingImg;

    // 객실 정보
    private List<RoomItem> roomItems;

    // insert하면서 pk 얻어와서 room insert 할 때 쓰기
    @JsonIgnore
    private long glampId;



}
