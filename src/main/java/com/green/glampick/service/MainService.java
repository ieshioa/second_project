package com.green.glampick.service;

import com.green.glampick.dto.response.main.GetMainGlampingListResponseDto;
import org.springframework.http.ResponseEntity;

public interface MainService {

    //  메인페이지 글램핑 리스트 불러오기  //
    ResponseEntity<? super GetMainGlampingListResponseDto> mainGlampingList();

}
