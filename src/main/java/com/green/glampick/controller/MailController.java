package com.green.glampick.controller;

import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import com.green.glampick.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "메일 컨트롤러")
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
        return service.sendAuthCode(userEmail);
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
        return service.checkCode(userEmail, authKey);
    }

}