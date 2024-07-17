package com.green.glampick.dto.response.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.object.ReviewListItem;
import com.green.glampick.dto.object.UserReviewListItem;
import com.green.glampick.entity.ReviewImageEntity;
import com.green.glampick.repository.resultset.GetUserReviewResultSet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

public class GetReviewResponseDto extends ResponseDto {

    long TotalReviewsCount;
    List<UserReviewListItem> reviewListItems;

    private GetReviewResponseDto(long totalReviewsCount, List<UserReviewListItem> reviewListItems) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.TotalReviewsCount = totalReviewsCount;
        this.reviewListItems = reviewListItems;
    }

    public static ResponseEntity<GetReviewResponseDto> success(long totalReviewsCount, List<UserReviewListItem> reviewListItems) {
        GetReviewResponseDto result = new GetReviewResponseDto(totalReviewsCount, reviewListItems);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> validateUserId() {
        ResponseDto result = new ResponseDto(ResponseCode.CANT_FIND_USER, ResponseMessage.CANT_FIND_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedBook() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOOK, ResponseMessage.NOT_EXISTED_BOOK);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedReview() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_REVIEW, ResponseMessage.NOT_EXISTED_REVIEW);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_PERMISSION, ResponseMessage.NOT_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }

}
