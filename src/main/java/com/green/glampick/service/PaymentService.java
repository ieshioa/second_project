package com.green.glampick.service;

import com.green.glampick.dto.request.payment.PostPaymentRequestDto;
import com.green.glampick.dto.response.payment.PostPaymentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PaymentService {

    ResponseEntity<? super PostPaymentResponseDto> postPayment(@RequestBody PostPaymentRequestDto p);
    ResponseEntity<? super GetPaymentResponseDto> getPayment(@RequestParam long reservationId);

}
