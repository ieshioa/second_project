package com.green.glampick.dto.object.glamping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomItem {
    @Schema(example = "카라반 102호", description = "객실 이름")
    private String roomName;
    @Schema(example = "65,500", description = "객실 가격")
    private int price;
    @Schema(example = "2", description = "기준 인원")
    private int peopleNum;
    @Schema(example = "6", description = "최대 인원")
    private int peopleMax;
    @Schema(example = "15:00:00", description = "체크인 시간")
    private String inTime;
    @Schema(example = "12:00:00", description = "체크아웃 시간")
    private String outTime;
    private List<String> roomImg;
    private List<Integer> service;

}
