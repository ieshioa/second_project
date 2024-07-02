package com.green.glampick.service.implement;

import com.green.glampick.dto.response.main.GetMainGlampingListResponseDto;
import com.green.glampick.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    @Override
    public ResponseEntity<? super GetMainGlampingListResponseDto> mainGlampingList() {
        return null;
    }

}