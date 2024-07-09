package com.green.glampick.dto.object.owner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookBeforeItem {
    @Schema(example = "1", description = "예약 PK")
    private long reservationId;
    @Schema(example = "0000000000000", description = "예약 번호")
    private String bookId;
    @Schema(example = "2024-07-08 12:00:00", description = "예약 일자")
    private String bookDate;
    @Schema(example = "카라반 102호", description = "예약한 객실 이름")
    private String roomName;
    @Schema(example = "김그린", description = "이용자 이름")
    private String name;
    @Schema(example = "010-1234-5678", description = "예약자 전화번호")
    private String phoneNum;
    @Schema(example = "2", description = "이용 인원")
    private int peopleNum;
    @Schema(example = "2024-07-01", description = "체크인 날짜")
    private String inDate;
    @Schema(example = "2024-07-02", description = "체크아웃 날짜")
    private String outDate;
    @Schema(example = "65,500", description = "최종 결제한 금액")
    private int totalPrice;
}
