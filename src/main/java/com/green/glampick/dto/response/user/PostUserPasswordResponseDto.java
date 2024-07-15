package com.green.glampick.dto.response.user;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class PostUserPasswordResponseDto extends ResponseDto {

    private boolean checkPw;

    private PostUserPasswordResponseDto(boolean checkPw) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.checkPw = checkPw;
    }

    private PostUserPasswordResponseDto(String code, String message, boolean checkPw) {
        super(code, message);
        this.checkPw = checkPw;
    }

    public static ResponseEntity<PostUserPasswordResponseDto> success() {
        PostUserPasswordResponseDto result = new PostUserPasswordResponseDto(true);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<PostUserPasswordResponseDto> invalidPassword() {
        PostUserPasswordResponseDto result
                = new PostUserPasswordResponseDto
                (ResponseCode.NOT_MATCH_PASSWORD, ResponseMessage.NOT_MATCH_PASSWORD, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> validateUserId() {
        ResponseDto result = new ResponseDto(ResponseCode.CANT_FIND_USER, ResponseMessage.CANT_FIND_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
