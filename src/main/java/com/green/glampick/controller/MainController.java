package com.green.glampick.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.green.glampick.dto.response.main.GetMainGlampingListResponseDto;
import com.green.glampick.service.MainService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
@Tag(name = "메인 컨트롤러")
public class MainController {

    private final MainService service;

    @GetMapping
    @Operation(summary = "인기 글램핑 리스트", description =
                    "<p> popular : 인기순 top3 </p>" +
                    "<p> petFriendly : 반려동물 동반 top3 </p>" +
                    "<p> mountainView : 마운틴 뷰 top3 </p>"
    )
    @ApiResponse(
            description =
                    "<p> <strong> ResponseCode 응답 코드 </strong> </p> " +
                            "<p> SU(200) : 리스트를 불러옴 </p> " +
                            "<p> DBE(500) : 데이터베이스 서버 오류 </p> ",
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetMainGlampingListResponseDto.class)
            )
    )
    public ResponseEntity<? super GetMainGlampingListResponseDto> mainGlampingList() {
        return service.mainGlampingList();
    }
}