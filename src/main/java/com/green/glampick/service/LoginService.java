package com.green.glampick.service;

import com.green.glampick.dto.request.login.SignInRequestDto;
import com.green.glampick.dto.request.login.SignUpRequestDto;
import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<? super PostSignUpResponseDto> signUpUser(SignUpRequestDto dto);
    ResponseEntity<? super PostSignInResponseDto> signInUser(SignInRequestDto dto);
}
