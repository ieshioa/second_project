package com.green.glampick.controller;

import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import com.green.glampick.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MailController {
    private final MailService service;

    @PostMapping("/mail-send")
    @Operation(summary = "이메일 인증 보내기", description = "<strong>입력한 이메일로 인증메일을 보냅니다.</strong>",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "성공에 대한 반환 값 입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostMailSendResponseDto.class)
                    ))})
    public ResponseEntity<? super PostMailSendResponseDto> sendMail(@RequestParam String userEmail) {
        ResponseEntity<? super PostMailSendResponseDto> response = service.sendAuthCode(userEmail);
        return response;
    }

    @PostMapping("/mail-check")
    @Operation(summary = "이메일 인증확인", description = "<strong>이메일 인증에 대해 인증번호를 확인합니다.</strong>",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "성공에 대한 반환 값 입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostMailCheckResponseDto.class)
                    ))})
    public ResponseEntity<? super PostMailCheckResponseDto> mailCheck(
            @RequestParam String userEmail, @RequestParam String authKey)
    {
        // Response 값에 대하여 true, false 값이 유동적으로 받아올려면, ResponseEntity 를 서비스에서 처리해야함으로
        // Controller 에서 ResponseEntity 로 반환하지 않게 처리.
        ResponseEntity<? super PostMailCheckResponseDto> response = service.checkCode(userEmail, authKey);
        return response;
    }

}