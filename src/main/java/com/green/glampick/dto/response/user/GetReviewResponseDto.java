package com.green.glampick.dto.response.user;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.entity.ReviewImageEntity;
import com.green.glampick.repository.resultset.GetUserReviewResultSet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

public class GetReviewResponseDto extends ResponseDto {


    private List<String> reviewimage;
    private List<GetUserReviewResultSet> userlist;

    private GetReviewResponseDto(List<GetUserReviewResultSet> userlist, List<ReviewImageEntity> reviewImageEntityList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        List<String> reviewimage = new ArrayList<>();
        for (ReviewImageEntity imageEntity : reviewImageEntityList) {
            String boardImage = imageEntity.getReviewImageName();
            reviewimage.add(boardImage);
        }
        this.userlist = userlist;
    }

    public static ResponseEntity<ResponseDto> success(List<GetUserReviewResultSet> userlist, List<ReviewImageEntity> reviewImageEntityList) {
        GetReviewResponseDto result = new GetReviewResponseDto(userlist, reviewImageEntityList);
//        ResponseDto result = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
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
