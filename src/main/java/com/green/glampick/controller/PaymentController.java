package com.green.glampick.controller;

import com.green.glampick.dto.request.payment.GetPaymentRequestDto;
import com.green.glampick.dto.request.payment.PostPaymentRequestDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.dto.response.payment.GetPaymentResponseDto;
import com.green.glampick.dto.response.payment.PostPaymentResponseDto;
import com.green.glampick.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@Tag(name = "결제 컨트롤러")
public class PaymentController {
    private final PaymentService service;

    @PostMapping
    @Operation(summary = "결제 정보 저장하기", description = "<strong>결제된 정보를 저장합니다.</strong>",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "성공에 대한 반환 값 입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostPaymentResponseDto.class)
                    ))})
    public ResponseEntity<? super PostPaymentResponseDto> postPayment (@RequestBody PostPaymentRequestDto p) {
        return service.postPayment(p);
    }

    @GetMapping
    @Operation(summary = "결제 정보 저장하기", description = "<strong>결제된 정보를 저장합니다.</strong>",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "성공에 대한 반환 값 입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostPaymentResponseDto.class)
                    ))})
    public ResponseEntity<? super GetPaymentResponseDto> getPayment (@ParameterObject GetPaymentRequestDto p) {
        return service.getPayment(p);
    }

}
