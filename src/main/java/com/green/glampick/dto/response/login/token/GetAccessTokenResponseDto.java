package com.green.glampick.dto.response.login.token;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
@Setter
public class GetAccessTokenResponseDto extends ResponseDto {

    Map map;

    private GetAccessTokenResponseDto(Map map) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.map = map;
    }

    public static ResponseEntity<ResponseDto> success(Map map) {
        GetAccessTokenResponseDto result = new GetAccessTokenResponseDto(map);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
