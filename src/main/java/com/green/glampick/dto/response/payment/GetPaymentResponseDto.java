package com.green.glampick.dto.response.payment;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.entity.PaymentEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class GetPaymentResponseDto extends ResponseDto {

    private String id;
    private long reservationId;
    private String pg;
    private long payAmount;

    private GetPaymentResponseDto(PaymentEntity paymentEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.id = paymentEntity.getId();
        this.reservationId = paymentEntity.getReservationId();
        this.pg = paymentEntity.getPg();
        this.payAmount = paymentEntity.getPayAmount();
    }

    public static ResponseEntity<GetPaymentResponseDto> success(PaymentEntity paymentEntity) {
        GetPaymentResponseDto result = new GetPaymentResponseDto(paymentEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedPayment() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PAYMENT, ResponseMessage.NOT_EXISTED_PAYMENT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_PERMISSION, ResponseMessage.NOT_PERMISSION);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
