package com.green.glampick.service;

import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import org.springframework.http.ResponseEntity;

public interface MailService {

    //  최초 실행 시, 초기화를 한번만 진행  //
    void init();

    //  6자리의 랜덤 숫자코드를 생성  //
    int createKey();

    //  이메일 인증 보내기  //
    ResponseEntity<? super PostMailSendResponseDto> sendAuthCode(String userEmail);

    //  이메일 코드 체크하기  //
    ResponseEntity<? super PostMailCheckResponseDto> checkCode(String userEmail, int authKey);
}