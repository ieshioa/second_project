package com.green.glampick.controller;

import com.green.glampick.dto.request.login.SignInRequestDto;
import com.green.glampick.dto.request.login.SignUpRequestDto;
import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<? super PostSignUpResponseDto> signUpUser(@RequestBody @Valid SignUpRequestDto dto) {
        ResponseEntity<? super PostSignUpResponseDto> response = service.signUpUser(dto);
        return response;
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
    public ResponseEntity<? super PostSignInResponseDto> signInUser(@RequestBody @Valid SignInRequestDto dto) {
        ResponseEntity<? super PostSignInResponseDto> response = service.signInUser(dto);
        return response;
    }

}