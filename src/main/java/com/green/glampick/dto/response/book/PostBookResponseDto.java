package com.green.glampick.dto.response.book;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostBookResponseDto extends ResponseDto {

    private PostBookResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<ResponseDto> success() {
        ResponseDto result = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> duplicatedBook() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_BOOK, ResponseMessage.DUPLICATE_BOOK);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_PERMISSION, ResponseMessage.NOT_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }

    public static ResponseEntity<ResponseDto> wrongDate() {
        ResponseDto result = new ResponseDto(ResponseCode.WRONG_DATE, ResponseMessage.WRONG_DATE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
