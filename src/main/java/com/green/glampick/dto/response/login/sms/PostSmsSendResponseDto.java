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

public class PostSmsSendResponseDto extends ResponseDto{

    private int phoneKey;

    private PostSmsSendResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    private PostSmsSendResponseDto(int phoneKey) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.phoneKey = phoneKey;
    }

    public static ResponseEntity<ResponseDto> success(int authKey) {
        PostSmsSendResponseDto result = new PostSmsSendResponseDto(authKey);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> invalidPhone() {
        ResponseDto result = new ResponseDto(ResponseCode.INVALID_PHONE, ResponseMessage.INVALID_PHONE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
