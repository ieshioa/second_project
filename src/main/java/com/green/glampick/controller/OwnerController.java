package com.green.glampick.controller;

import com.green.glampick.dto.request.GlampingPost;
import com.green.glampick.dto.response.owner.GetOwnerBookListResponseDto;
import com.green.glampick.dto.response.owner.PostGlampingInfoResponseDto;
import com.green.glampick.service.OwnerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owner")
@Tag(name = "사장님 컨트롤러")
public class OwnerController {

    private final OwnerService service;

// 민지 =================================================================================================================
    @PostMapping
    @Operation(summary = "글램핑 정보 등록", description =
            "<p> <strong> extraCharge(추가요금) 제외 모든 데이터가 필수 입력입니다. </strong> </p>" +
                    "<p> 사진 업로드를 위해 테스트는 포스트맨에서 해주세요 ~ </p>"
    )
    @ApiResponse(
            description =
                    "<p> <strong> ResponseCode 응답 코드 </strong> </p> " +
                            "<p> SU(200) : 글램핑 등록 성공 </p> " +
                            "<p> VF(400) : 입력되지 않은 데이터가 존재 </p> " +
                            "<p> DL(400) : 위치 정보 중복됨 </p> " +
                            "<p> DBE(500) : 데이터베이스 서버 오류 </p> ",
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostGlampingInfoResponseDto.class)
            )
    )
    public ResponseEntity<? super PostGlampingInfoResponseDto> postGlampingInfo(@RequestBody GlampingPost glampingPostReq) {
        return service.postGlampingInfo(glampingPostReq);
    }

    @GetMapping("book/{glamp_id}")
    @Operation(summary = "글램핑 예약 내역 불러오기", description =
            "<strong> <p> glamp_id (글램핑 PK) 는 필수입력입니다 </p> </strong>" +
                    "<p> before : 이용 전 / 중 </p>" +
                    "<p> complete : 이용 완 </p>" +
                    "<p> cancel : 취소 </p>"
    )
    @ApiResponse(
            description =
                    "<p> <strong> ResponseCode 응답 코드 </strong> </p> " +
                            "<p> SU(200) : 예약 내역 불러오기 성공 </p> " +
                            "<p> RN(200) : 예약 내역 없음 </p> " +
                            "<p> VF(400) : 글램핑 PK가 입력되지 않음 </p> " +
                            "<p> DBE(500) : 데이터베이스 서버 오류 </p> ",
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetOwnerBookListResponseDto.class)
            )
    )
    public ResponseEntity<? super GetOwnerBookListResponseDto> getGlampReservation(@PathVariable("glamp_id") long glampId) {
        return service.getGlampReservation(glampId);
    }

// 강국 =================================================================================================================


}

