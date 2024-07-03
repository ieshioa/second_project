package com.green.glampick.controller;

import com.green.glampick.dto.request.payment.PostPaymentRequestDto;
import com.green.glampick.dto.response.payment.PostPaymentResponseDto;
import com.green.glampick.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService service;

    @PostMapping
    public ResponseEntity<? super PostPaymentResponseDto> postPayment (@RequestBody PostPaymentRequestDto p) {
        return service.postPayment(p);
    }

}
