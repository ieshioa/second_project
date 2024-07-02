package com.green.glampick.dto.object.glamping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GlampingReviewItem {
    @Schema(example = "박보영귀여워", description = "리뷰 작성자")
    private String userName;
    @Schema(example = "진짜 너무너무 좋은 숙소였어요~!", description = "리뷰 내용")
    private String content;
    @Schema(example = "1329명", description = "리뷰 평가자 숫자")
    private int countReviewUsers;
}
