package com.green.glampick.dto.response.owner;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostGlampingInfoResponseDto extends ResponseDto {

    private String data = "ㅋㅋ";

    public PostGlampingInfoResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<ResponseDto> success() {
        ResponseDto result = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> isNull() {
        ResponseDto result = new ResponseDto(ResponseCode.VALIDATION_FAILED, ResponseMessage.VALIDATION_FAILED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> validateUserId() {
        ResponseDto result = new ResponseDto(ResponseCode.AAA, ResponseMessage.AAA);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> hasExistingGlamping() {
        ResponseDto result = new ResponseDto(ResponseCode.BBB, ResponseMessage.BBB);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> existingLocation() {
        ResponseDto result = new ResponseDto(ResponseCode.CCC, ResponseMessage.CCC);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> badPrice() {
        ResponseDto result = new ResponseDto(ResponseCode.DDD, ResponseMessage.DDD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> badPeople() {
        ResponseDto result = new ResponseDto(ResponseCode.EEE, ResponseMessage.EEE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
