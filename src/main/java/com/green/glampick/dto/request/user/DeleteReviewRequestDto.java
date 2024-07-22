package com.green.glampick.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DeleteReviewRequestDto {

    private long reviewId;
    @JsonIgnore
    private long userId;
}
