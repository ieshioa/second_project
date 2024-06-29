package com.green.glampick.service.implement;

import com.green.glampick.mapper.SocialLoginMapper;
import com.green.glampick.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements SocialLoginService {
    private final SocialLoginMapper mapper;


}
