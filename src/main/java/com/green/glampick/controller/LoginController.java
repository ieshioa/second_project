package com.green.glampick.controller;

import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import com.green.glampick.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "로그인 및 회원가입 컨트롤러")
public class LoginController {
    private final LoginService service;

    @PostMapping("/sign-up")
    @Operation(summary = "이메일 회원가입", description = "<strong>이메일을 통한 회원가입을 진행합니다.</strong>" +
                "<p>이메일 인증을 받은 후, 회원가입이 가능합니다.</p>",
                responses = {@ApiResponse(
                    responseCode = "200",
                    description = "성공에 대한 반환 값 입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostSignUpResponseDto.class)
                    ))})
    public ResponseEntity<? super PostSignUpResponseDto> signUpUser() {
        ResponseEntity<? super PostSignUpResponseDto> response = service.signUpUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "이메일 로그인", description = "<strong>이메일을 통한 로그인을 진행합니다.</strong>",
               responses = {@ApiResponse(
                   responseCode = "200",
                   description = "성공에 대한 반환 값 입니다.",
                   content = @Content(
                           mediaType = "application/json",
                           schema = @Schema(implementation = PostSignInResponseDto.class)
                   ))})
    public ResponseEntity<? super PostSignInResponseDto> signInUser() {
        ResponseEntity<? super PostSignInResponseDto> response = service.signInUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/mail-send")
    @Operation(summary = "이메일 인증 보내기", description = "<strong>입력한 이메일로 인증메일을 보냅니다.</strong>",
               responses = {@ApiResponse(
                    responseCode = "200",
                    description = "성공에 대한 반환 값 입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostMailSendResponseDto.class)
                    ))})
    public ResponseEntity<? super PostMailSendResponseDto> mailSend() {
        ResponseEntity<? super PostMailSendResponseDto> response = service.mailSend();
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
    public ResponseEntity<? super PostMailCheckResponseDto> mailCheck() {
        ResponseEntity<? super PostMailCheckResponseDto> response = service.mailCheck();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}