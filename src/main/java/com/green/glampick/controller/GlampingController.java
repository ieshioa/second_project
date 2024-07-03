package com.green.glampick.controller;

import com.green.glampick.dto.request.glamping.GetInfoRequestDto;
import com.green.glampick.dto.request.glamping.ReviewInfoRequestDto;
import com.green.glampick.dto.response.glamping.GetGlampingInformationResponseDto;
import com.green.glampick.dto.response.glamping.GetGlampingReviewInfoResponseDto;
import com.green.glampick.dto.response.glamping.GetSearchGlampingListResponseDto;
import com.green.glampick.dto.response.glamping.favorite.GetFavoriteGlampingResponseDto;
import com.green.glampick.service.GlampingService;
import com.green.glampick.dto.request.GlampingSearch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/glamping")
@Tag(name = "글램핑 컨트롤러")
public class GlampingController {
    private final GlampingService service;


// 민지 =================================================================================================================
    @GetMapping("search")
    @Operation(summary = "글램핑 검색 결과 가져오기", description =
            "<p></p><strong> searchWord : 선택 입력 </strong> <p></p>" +
                    "<strong> 나머지 : 필수 입력 </strong> ")
    @ApiResponse(
            description =
                    "<p> <strong> ResponseCode 응답 코드 </strong> </p> " +
                            "<p> SU(200) : 검색 결과를 불러옴 </p> " +
                            "<p> RN(200) : 검색 결과가 없음 </p> " +
                            "<p> DBE(500) : 데이터베이스 서버 오류 </p> ",
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetSearchGlampingListResponseDto.class)
            )
    )
    public ResponseEntity<? super GetSearchGlampingListResponseDto> searchGlamping(@ParameterObject @ModelAttribute GlampingSearch searchReq) {
        return service.searchGlamping();
    }


// 강국 =================================================================================================================
    @GetMapping("favorite/{glamp_id}")
    @Operation(
            summary = "관심 글램핑 등록" ,
            description = "<strong> 변수명 glampId :  글램프 PK </strong> <p>  ex)23 </p>",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공에 대한 반환 값 입니다." +
                                    "<p> [글램핑 관심등록] </p>" +
                                    "<p> result: 0 >> 글램핑 관심 등록 </p>" +
                                    "<p> result: 1 >> 글램핑 관심 취소</p>",

                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetFavoriteGlampingResponseDto.class)
                            )
                    )
            }

    )
    public ResponseEntity<? super GetFavoriteGlampingResponseDto> favoriteGlamping(@PathVariable long glampId) {
        return service.favoriteGlamping(glampId);
    }

    @Operation(summary = "글램핑 상세 페이지",
            description = "<strong> 변수명 glampId : 글램프 PK </strong> <p>  ex)23 </p>",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공에 대한 반환 값 입니다." +
                                    "<p> [글램핑 정보]" +
                                    "<p> glampName:  글램핑장 이름 </p>" +
                                    "<p> starPointAvg: 별점 </p>" +
                                    "<p> glampLocation: 글램핑장 주소</p>" +
                                    "<p> glampIntro: 글램핑 소개글</p>" +
                                    "<p> infoBasic: 기본 정보</p>" +
                                    "<p> infoParking: 주차장 정보</p>" +
                                    "<p> infoNotice: 유의 사항</p>" +
                                    "<p> [리뷰 정보] </p>" +
                                    "<p> userName: 유저닉네임</p>" +
                                    "<p> content: 리뷰내용</p>" +
                                    "<p> countReviewUsers: 리뷰인원수</p>" +
                                    "<p> [객실정보] </p>" +
                                    "<p> roomPics: 객실 사진</p>" +
                                    "<p> roomId: 객실 PK</p>" +
                                    "<p> glampId: 글램핑 PK</p>" +
                                    "<p> roomPrice: 객실 가격</p>" +
                                    "<p> roomNumPeople: 객실 기본인원 수</p>" +
                                    "<p> roomMaxPeople: 객실 최대인원 수</p>" +
                                    "<p> checkInTime: 체크인 시간</p>" +
                                    "<p> checkOutTime: 체크아웃 시간</p>"
                            ,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetGlampingInformationResponseDto.class)
                            ))})
    @GetMapping("info")
    public ResponseEntity<? super GetGlampingInformationResponseDto> getInfoGlampingDetail(@ParameterObject @RequestParam GetInfoRequestDto p) {
        return service.getInfoGlampingDetail(p);
    }
    @Operation(summary = "글램핑 리뷰 페이지",
            description = "<strong> 변수명 glampId : 글램프 PK </strong> <p>  ex)35 </p>",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공에 대한 반환 값 입니다."
                            ,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetGlampingReviewInfoResponseDto.class)
                            ))})
    @GetMapping("{glamp_id}/review")
    public ResponseEntity<? super GetGlampingReviewInfoResponseDto> getInfoReviewList(@ParameterObject @ModelAttribute ReviewInfoRequestDto p) {
        return service.getInfoReviewList(p);

    }

}
