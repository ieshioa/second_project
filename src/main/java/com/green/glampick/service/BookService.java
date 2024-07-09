package com.green.glampick.service;

import com.green.glampick.dto.request.book.postBookRequestDto;
import com.green.glampick.dto.response.book.postBookResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookService {

    //  글램핑 예약하기  //
    ResponseEntity<? super postBookResponseDto> postBook(postBookRequestDto dto);

}
