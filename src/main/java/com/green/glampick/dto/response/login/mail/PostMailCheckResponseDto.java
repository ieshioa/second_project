package com.green.glampick.dto.response.login.mail;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class PostMailCheckResponseDto extends ResponseDto {

    private boolean authCheck;

    private PostMailCheckResponseDto(boolean authCheck) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.authCheck = authCheck;
    }

    private PostMailCheckResponseDto(String code, String message, boolean authCheck) {
        super(code, message);
        this.authCheck = authCheck;
    }

    public static ResponseEntity<PostMailCheckResponseDto> success() {
        PostMailCheckResponseDto result = new PostMailCheckResponseDto(true);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> expiredCode() {
        ResponseDto result = new ResponseDto(ResponseCode.EXPIRED_CODE, ResponseMessage.EXPIRED_CODE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<PostMailCheckResponseDto> invalidCode() {
        PostMailCheckResponseDto result = new PostMailCheckResponseDto(ResponseCode.INVALID_CODE, ResponseMessage.INVALID_CODE, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}