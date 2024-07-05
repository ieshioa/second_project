package com.green.glampick.dto.response.owner;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class PostRoomInfoResponseDto extends ResponseDto {

    // 테스트용 스웨거에서 보여주기
    private long roomId;

    public PostRoomInfoResponseDto(long roomId) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.roomId = roomId;
    }

    public static ResponseEntity<ResponseDto> success(long roomId) {
        PostRoomInfoResponseDto result = new PostRoomInfoResponseDto(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // req가 올바르지 않음
    public static ResponseEntity<ResponseDto> validationFailed(String errorMsg) {
        ResponseDto result = new ResponseDto(ResponseCode.VALIDATION_FAILED, errorMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    // 이미지 업로드 과정에서 오류 발생
    public static ResponseEntity<ResponseDto> fileUploadError() {
        ResponseDto result = new ResponseDto(ResponseCode.GGG, ResponseMessage.GGG);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

}