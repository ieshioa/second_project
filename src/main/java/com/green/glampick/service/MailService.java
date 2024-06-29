package com.green.glampick.service;

import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import org.springframework.http.ResponseEntity;

public interface MailService {

    public void init();
    public String createKey();
    public ResponseEntity<? super PostMailSendResponseDto> sendAuthCode(String userEmail);
    public void checkCode(String userEmail, String key);
}