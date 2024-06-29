package com.green.glampick.service;

import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import org.springframework.http.ResponseEntity;

public interface MailService {

    void init();
    String createKey();
    ResponseEntity<? super PostMailSendResponseDto> sendAuthCode(String userEmail);

    ResponseEntity<? super PostMailCheckResponseDto> checkCode(String userEmail, String authKey);
}