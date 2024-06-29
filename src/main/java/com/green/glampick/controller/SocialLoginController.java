package com.green.glampick.controller;

import com.green.glampick.service.SocialLoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/oauth2")
@Tag(name = "소셜로그인 컨트롤러")
public class SocialLoginController {
    private final SocialLoginService service;


}
