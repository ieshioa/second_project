package com.green.glampick.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Setter;

@Data
public class ReviewPatchRequestDto {
    @JsonIgnore
    private long userId;
    @Schema(example = "5", description = "리뷰 PK")
    private long reviewId;
}
