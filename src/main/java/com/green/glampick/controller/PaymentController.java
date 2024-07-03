package com.green.glampick.controller;

import com.green.glampick.dto.request.payment.PostPaymentRequestDto;
import com.green.glampick.dto.response.payment.GetPaymentResponseDto;
import com.green.glampick.dto.response.payment.PostPaymentResponseDto;
import com.green.glampick.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<? super GetPaymentResponseDto> getPayment (@RequestParam long reservationId) {
        return service.getPayment(reservationId);
    }

}
