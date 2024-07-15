package com.green.glampick.dto.response.user;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.entity.ReviewEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostReviewResponseDto extends ResponseDto {

    private long reviewId;
    private long userId;
    private long glampId;
    private String reviewContent;
    private long reviewStarPoint;
    private String createdAt;
    private long roomId;
    private List<MultipartFile> reviewImageEntityList;

    private PostReviewResponseDto(ReviewEntity reviewEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.reviewId = reviewEntity.getReviewId();
        this.glampId = reviewEntity.getGlampId();
        this.userId = reviewEntity.getUserId();
        this.reviewContent = reviewEntity.getReviewContent();
        this.reviewStarPoint = reviewEntity.getReviewStarPoint();
        this.createdAt = reviewEntity.getCreatedAt();
        this.roomId = reviewEntity.getRoomId();
//        this.reviewPics = reviewEntity.getr

    }
    //

    public static ResponseEntity<ResponseDto> success() {
        ResponseDto result = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_PERMISSION, ResponseMessage.NOT_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }

    public static ResponseEntity<ResponseDto> validateUserId() {
        ResponseDto result = new ResponseDto(ResponseCode.CANT_FIND_USER, ResponseMessage.CANT_FIND_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
