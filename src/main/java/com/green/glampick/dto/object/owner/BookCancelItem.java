package com.green.glampick.dto.object.owner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCancelItem {
    @Schema(example = "0000000001234", description = "예약 번호")
    private long reservationNum;
    @Schema(example = "김그린", description = "이용자 이름")
    private String name;
    @Schema(example = "010-1234-5678", description = "예약자 전화번호")
    private String phoneNum;
    @Schema(example = "카라반 102호", description = "예약한 객실 이름")
    private String roomName;
    @Schema(example = "2024-07-01", description = "체크인 날짜")
    private String inDate;
    @Schema(example = "2024-07-02", description = "체크아웃 날짜")
    private String outDate;
    @Schema(example = "65,500", description = "최종 결제한 금액")
    private int totalPrice;
    @Schema(example = "취소 사유", description = "취소 사유")
    private String comment;
    @Schema(example = "2024-06-30 12:00:00", description = "예약 시간 (이용시간x 예약(결제)한 시간임)")
    private String createAt;
}
