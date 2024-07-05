package com.green.glampick.dto.response.user;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.repository.resultset.GetUserReviewResultSet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Setter
@Getter

public class GetReviewResponseDto extends ResponseDto {
//    private long reviewStarPoint; // 별점
//    private String createdAt; // 리뷰 작성 날짜
//    private String reviewImageId; // 글랭핑 사진
//    private String userProfileImage; // 유저 사진
//    private String userNickname; // 유저 닉네임
//    private String glampName;// 글램핑 이름
//    private String roomName;// 글램핑 호수
//    private String reviewContent; // 리뷰
//    private String reviewComment; // 숙소 답변

    private List<GetUserReviewResultSet> userlist;

    private GetReviewResponseDto(List<GetUserReviewResultSet> userlist) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userlist = userlist;
    }

    public static ResponseEntity<ResponseDto> success(List<GetUserReviewResultSet> userlist) {
        GetReviewResponseDto result = new GetReviewResponseDto(userlist);
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
