package com.green.glampick.dto.object.glamping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
public class GlampingRoomListItem {

    private List<String> roomPics;

    @Schema(example = "5", description = "객실 pk")
    private long roomId;
    @Schema(example = "7", description = "글램핑 pk")
    private long glampId;
    @Schema(example = "우당탕탕카라반", description = "객실네임")
    private String roomName;
    @Schema(example = "137,000", description = "객실 가격")
    private String roomPrice;
    @Schema(example = "기준 2명", description = "기준 명 수")
    private int roomNumPeople;
    @Schema(example = "최대 5명", description = "최대 명 수")
    private int roomMaxPeople;
    @Schema(example = "15:00", description = "체크인 시간")
    private String checkInTime;
    @Schema(example = "23:00", description = "체크아웃 시간")
    private String checkOutTime;
}
