package com.green.glampick.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Setter
@Getter
public class PostReviewRequestDto {

    @JsonIgnore private long userId;
    private long reservationId;
    private String reviewContent;
    private long reviewStarPoint;


}
