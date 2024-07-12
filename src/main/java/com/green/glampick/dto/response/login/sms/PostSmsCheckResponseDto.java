package com.green.glampick.dto.response.login.sms;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class PostSmsCheckResponseDto extends ResponseDto {

    private boolean phoneCheck;

    private PostSmsCheckResponseDto(boolean phoneCheck) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.phoneCheck = phoneCheck;
    }

    private PostSmsCheckResponseDto(String code, String message, boolean phoneCheck) {
        super(code, message);
        this.phoneCheck = phoneCheck;
    }

    public static ResponseEntity<PostSmsCheckResponseDto> success() {
        PostSmsCheckResponseDto result = new PostSmsCheckResponseDto(true);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> expiredCode() {
        ResponseDto result = new ResponseDto(ResponseCode.EXPIRED_CODE, ResponseMessage.EXPIRED_CODE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<PostSmsCheckResponseDto> invalidCode() {
        PostSmsCheckResponseDto result = new PostSmsCheckResponseDto(ResponseCode.INVALID_CODE, ResponseMessage.INVALID_CODE, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
