package com.green.glampick.service;

import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<? super PostSignUpResponseDto> signUpUser();
    ResponseEntity<? super PostSignInResponseDto> signInUser();
    ResponseEntity<? super PostMailSendResponseDto> mailSend();
    ResponseEntity<? super PostMailCheckResponseDto> mailCheck();
}
