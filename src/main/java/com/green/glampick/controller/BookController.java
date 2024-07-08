package com.green.glampick.controller;

import com.green.glampick.dto.request.book.postBookRequestDto;
import com.green.glampick.dto.response.book.postBookResponseDto;
import com.green.glampick.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService service;

    @PostMapping
    public ResponseEntity<? super postBookResponseDto> postBook(@RequestBody postBookRequestDto dto) {
        return service.postBook(dto);
    }
}
