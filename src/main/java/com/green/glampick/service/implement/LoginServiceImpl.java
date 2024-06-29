package com.green.glampick.service.implement;

import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import com.green.glampick.mapper.LoginMapper;
import com.green.glampick.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginMapper mapper;

    @Override
    public ResponseEntity<? super PostSignUpResponseDto> signUpUser() {
        return null;
    }

    @Override
    public ResponseEntity<? super PostSignInResponseDto> signInUser() {
        return null;
    }

    @Override
    public ResponseEntity<? super PostMailSendResponseDto> mailSend() {
        return null;
    }

    @Override
    public ResponseEntity<? super PostMailCheckResponseDto> mailCheck() {
        return null;
    }
}
