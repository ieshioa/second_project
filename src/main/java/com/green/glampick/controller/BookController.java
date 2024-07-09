package com.green.glampick.controller;

import com.green.glampick.dto.request.book.postBookRequestDto;
import com.green.glampick.dto.response.book.postBookResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "글램핑 예약하기", description = "<strong>글램핑 예약을 할 수 있습니다.</strong>",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "성공에 대한 반환 값 입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = postBookResponseDto.class)
                    ))})
    public ResponseEntity<? super postBookResponseDto> postBook(@RequestBody postBookRequestDto dto) {
        return service.postBook(dto);
    }
}
