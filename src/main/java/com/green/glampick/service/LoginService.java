package com.green.glampick.service;

import com.green.glampick.dto.request.login.SignInRequestDto;
import com.green.glampick.dto.request.login.SignUpRequestDto;
import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.dto.response.login.sms.PostSmsCheckResponseDto;
import com.green.glampick.dto.response.login.sms.PostSmsSendResponseDto;
import com.green.glampick.dto.response.login.token.GetAccessTokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LoginService {

    void init();

    ResponseEntity<? super PostSignUpResponseDto> signUpUser(SignUpRequestDto dto);
    ResponseEntity<? super PostSignInResponseDto> signInUser(HttpServletResponse res, SignInRequestDto dto);
    ResponseEntity<? super GetAccessTokenResponseDto> getAccessToken(HttpServletRequest req);

    ResponseEntity<? super PostSmsSendResponseDto> sendOne(String userPhone);
    ResponseEntity<? super PostSmsCheckResponseDto> checkPhone(String userPhone, int phoneKey);

    int createKey();
}
