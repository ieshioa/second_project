package com.green.glampick.controller;

import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import com.green.glampick.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/api")
public class MailController {
    private final MailService service;

    @PostMapping("/mail-send")
    public ResponseEntity<? super PostMailSendResponseDto> sendMail(@RequestParam String userEmail) {
        ResponseEntity<? super PostMailSendResponseDto> response = service.sendAuthCode(userEmail);
        return response;
    }


}
