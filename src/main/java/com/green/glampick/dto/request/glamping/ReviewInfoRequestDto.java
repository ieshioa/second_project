package com.green.glampick.dto.request.glamping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReviewInfoRequestDto {
 @Schema(example = "35", description = "글램핑PK")
 private long glampId;
 @JsonIgnore
 private long userId;
}
