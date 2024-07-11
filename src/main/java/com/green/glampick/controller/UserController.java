package com.green.glampick.controller;

import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.user.*;
import com.green.glampick.entity.ReviewImageEntity;
import com.green.glampick.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 컨트롤러")
public class UserController {
    private final UserService service;

    @GetMapping("/book")// 예약 내역 불러오기
    @Operation(summary = "예약 내역 불러오기", description = "<strong></strong>" +
            "<p> reservationId = </p>" +
            "<p> reviewContent = </p>" +
            "<p> reviewStarPoint = </p>")
    public ResponseEntity<?super GetBookResponseDto> getBook(@ParameterObject GetBookRequestDto dto) {
        return service.getBook(dto);
    }

    @PostMapping("/book-cancel")// 예약 내역 취소하기
    @Operation(summary = "예약 취소", description = "<strong></strong>" +
            "<p></p>")
    public ResponseEntity<?super CancelBookResponseDto> cancelBook(@RequestBody CancelBookRequestDto dto) {
        return service.cancelBook(dto);
    }

    @PostMapping("/review")// 리뷰 작성
    @Operation(summary = "리뷰 작성", description = "<strong></strong>" +
            "<p> reservationId = 예약 ID ex) 1 </p>" +
            "<p> reviewContent = 리뷰 내용 ex) 정말 좋았다.</p>" +
            "<p> reviewStarPoint = 별점 ex) 3 </p>")
    public ResponseEntity<?super PostReviewResponseDto> postReview(@RequestPart PostReviewRequestDto dto, @RequestPart List<MultipartFile> mf) {
        return service.postReview(dto, mf);
    }

    @DeleteMapping("/delete")// 리뷰 삭제
    @Operation(summary = "리뷰 삭제", description = "<strong></strong>" +
            "<p> review_id = 리뷰 pk ex) 1 </p>")//
    public ResponseEntity<?super DeleteReviewResponseDto> deleteReview(@RequestParam("review_id") long reviewId) {
        return service.deleteReview(reviewId);

    }

    @GetMapping("/review")// 리뷰 불러오기
    @Operation(summary = "리뷰 불러오기", description = "<strong></strong>" +
            "<p> review_id = 리뷰 pk ex) 1 </p>")
    public ResponseEntity<?super GetReviewResponseDto> getReview(@ParameterObject @ModelAttribute GetReviewRequestDto dto) {
        return service.getReview(dto);
    }

    @GetMapping("/favorite-glamping")// 관심 글램핑 불러오기
    @Operation(summary = "관심 글램핑 불러오기", description = "<strong></strong>" +
            "<p></p>")
    public ResponseEntity<?super GetFavoriteGlampingResponseDto> getFavoriteGlamping(@ParameterObject @ModelAttribute GetFavoriteGlampingRequestDto dto) {
        return service.getFavoriteGlamping(dto);
    }

//    @GetMapping("/coupon")// 쿠폰 불러오기
//    public ResponseEntity<?super PostReviewResponseDto> getCoupon(@ParameterObject @ModelAttribute String email) {
//        ResponseEntity<? super PostReviewResponseDto> response = service.getCoupon(email);
//        return response;
//    }

    @GetMapping("")// 유저 정보 불러오기
    @Operation(summary = "유저 정보 불러오기", description = "<strong></strong>" +
            "<p></p>")
    public ResponseEntity<?super GetUserResponseDto> getUser(@ParameterObject GetUserRequestDto dto) {
        return service.getUser(dto);
    }

    @PutMapping// 유저 정보 수정하기
    @Operation(summary = "유저 정보 수정하기", description = "<strong></strong>" +
            "<p></p>")
    public ResponseEntity<?super UpdateUserResponseDto> updateUser
            (@RequestPart UpdateUserRequestDto dto, @RequestPart  MultipartFile mf)
    {
        return service.updateUser(dto, mf);
    }

    @DeleteMapping("")// 회원탈퇴
    @Operation(summary = "회원탈퇴", description = "<strong></strong>" +
            "<p></p>")
    public ResponseEntity<?super DeleteUserResponseDto> deleteUser(@RequestParam("user_id") int userId) {
        return service.deleteUser(userId);
    }

}

