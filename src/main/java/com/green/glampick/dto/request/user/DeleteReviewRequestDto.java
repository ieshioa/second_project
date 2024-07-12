package com.green.glampick.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteReviewRequestDto {

    private long reviewId;
    @JsonIgnore
    private long userId;
}
