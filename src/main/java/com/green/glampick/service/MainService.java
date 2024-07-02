package com.green.glampick.service;

import com.green.glampick.dto.response.main.GetMainGlampingListResponseDto;
import org.springframework.http.ResponseEntity;

public interface MainService {
    ResponseEntity<? super GetMainGlampingListResponseDto> mainGlampingList();
}
