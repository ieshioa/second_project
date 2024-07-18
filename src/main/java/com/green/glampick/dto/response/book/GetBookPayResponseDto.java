package com.green.glampick.dto.response.book;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class GetBookPayResponseDto extends ResponseDto {

    private long payAmount;

    private GetBookPayResponseDto(long payAmount) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.payAmount = payAmount;
    }

    public static ResponseEntity<GetBookPayResponseDto> success(long payAmount) {
        GetBookPayResponseDto result = new GetBookPayResponseDto(payAmount);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
