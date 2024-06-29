package com.green.glampick.service.implement;

import com.green.glampick.dto.response.talk.PostKakaoTalkResponseDto;
import com.green.glampick.mapper.TalkMapper;
import com.green.glampick.service.TalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalkServiceImpl implements TalkService {
    private final TalkMapper mapper;

    @Override
    public ResponseEntity<? super PostKakaoTalkResponseDto> postKakaoTalk() {
        return null;
    }

}