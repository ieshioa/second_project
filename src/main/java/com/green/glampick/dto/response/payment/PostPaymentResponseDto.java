package com.green.glampick.dto.response.payment;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class PostPaymentResponseDto extends ResponseDto {

    private String id;

    private PostPaymentResponseDto(String id) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.id = id;
    }

    public static ResponseEntity<PostPaymentResponseDto> success(String id) {
        PostPaymentResponseDto result = new PostPaymentResponseDto(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
