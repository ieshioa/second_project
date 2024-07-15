package com.green.glampick.service;

import com.green.glampick.dto.request.book.postBookRequestDto;
import com.green.glampick.dto.response.book.PostBookResponseDto;
import org.springframework.http.ResponseEntity;

public interface BookService {

    //  글램핑 예약하기  //
    ResponseEntity<? super PostBookResponseDto> postBook(postBookRequestDto dto);

}
