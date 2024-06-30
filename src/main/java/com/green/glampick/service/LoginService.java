package com.green.glampick.service;

import com.green.glampick.dto.request.login.SignInRequestDto;
import com.green.glampick.dto.request.login.SignUpRequestDto;
import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LoginService {

    ResponseEntity<? super PostSignUpResponseDto> signUpUser(SignUpRequestDto dto);
    ResponseEntity<? super PostSignInResponseDto> signInUser(HttpServletResponse res, SignInRequestDto dto);
    Map getAccessToken(HttpServletRequest req);
}
