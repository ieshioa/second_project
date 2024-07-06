package com.green.glampick.service;

import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface UserService {

    //  마이페이지 - 예약 내역 불러오기  //
    ResponseEntity<?super GetBookResponseDto> getBook(GetBookRequestDto dto);

    //  마이페이지 - 예약 취소하기  //
    ResponseEntity<?super CancelBookResponseDto> cancelBook(CancelBookRequestDto dto);

    //  마이페이지 - 리뷰 작성하기  //
    ResponseEntity<?super PostReviewResponseDto> postReview(PostReviewRequestDto dto);

    //  마이페이지 - 리뷰 삭제하기  //
    ResponseEntity<?super DeleteReviewResponseDto> deleteReview(long reviewId);

    //  마이페이지 - 리뷰 불러오기  //
    ResponseEntity<?super GetReviewResponseDto> getReview(GetReviewRequestDto dto);

    //  마이페이지 - 관심 글램핑 불러오기  //
    ResponseEntity<?super GetFavoriteGlampingResponseDto> getFavoriteGlamping(GetFavoriteGlampingRequestDto dto);

    //  마이페이지 - 내 정보 불러오기  //
    ResponseEntity<?super GetUserResponseDto> getUser(GetUserRequestDto dto);

    //  마이페이지 - 내 정보 수정하기  //
    ResponseEntity<?super UpdateUserResponseDto> updateUser(UpdateUserRequestDto dto);

    //  마이페이지 - 회원 탈퇴  //
    ResponseEntity<?super DeleteUserResponseDto> deleteUser(long userId);

}
