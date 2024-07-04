package com.green.glampick.service;

import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?super GetBookResponseDto> getBook(GetBookRequestDto dto);// 예약 내역 불러오기
    ResponseEntity<?super CancelBookResponseDto> cancelBook(CancelBookRequestDto dto);//예약 내역 취소하기
    ResponseEntity<?super PostReviewResponseDto> postReview(PostReviewRequestDto dto);//리뷰 작성
    ResponseEntity<?super DeleteReviewResponseDto> deleteReview(int email);//리뷰 삭제
    ResponseEntity<?super GetReviewResponseDto> getReview(GetReviewRequestDto email);//리뷰 불러오기
    ResponseEntity<?super GetFavoriteGlampingListResponseDto> getFavoriteGlamping(GetFavoriteGlampingRequestDto email);// 관심 글램핑 불러오기
    //     ResponseEntity<?super PostReviewResponseDto> getCoupon(String email);//쿠폰 불러오기
    ResponseEntity<?super GetUserResponseDto> getUser(GetUserRequestDto dto);//유저 정보 불러오기
    ResponseEntity<?super UpdateUserResponseDto> updateUser(UpdateUserRequestDto dto);//유저 정보 수정하기
    ResponseEntity<?super DeleteUserResponseDto> deleteUser(int userId);//회원 탈퇴

}
