package com.green.glampick.dto.object.glamping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
public class GlampingRoomListItem {

    private List<String> roomServices;

    @Schema(example = "5", description = "객실 pk")
    private long roomId;
    @Schema(example = "cb4a0b8f-629e-4d20-9626-cd45347837df.png", description = "객실사진")
    private String pic;
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
