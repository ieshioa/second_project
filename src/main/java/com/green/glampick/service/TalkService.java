package com.green.glampick.service;

import com.green.glampick.dto.response.talk.PostKakaoTalkResponseDto;
import org.springframework.http.ResponseEntity;

public interface TalkService {
    ResponseEntity<? super PostKakaoTalkResponseDto> postKakaoTalk();
}
