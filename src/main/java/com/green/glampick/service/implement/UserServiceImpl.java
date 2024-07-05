package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.user.*;
import com.green.glampick.entity.ReservationEntity;
import com.green.glampick.entity.ReviewEntity;
import com.green.glampick.entity.UserEntity;
import com.green.glampick.repository.ReservationRepository;
import com.green.glampick.repository.ReviewRepository;
import com.green.glampick.repository.UserRepository;
import com.green.glampick.repository.resultset.GetBookResultSet;
import com.green.glampick.repository.resultset.GetUserReviewResultSet;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;



    @Override //예약 불러오기
    public ResponseEntity<? super GetBookResponseDto> getBook(GetBookRequestDto dto) {
        dto.setUserId(authenticationFacade.getLoginUserId());

        List<GetBookResultSet> resultSets;

        try {
            resultSets = reservationRepository.getBook(dto.getUserId());
            if (resultSets == null) {
                return GetBookResponseDto.noExistedBook();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBookResponseDto.success(resultSets);
    }

    @Override // 예약 취소하기
    public ResponseEntity<? super PatchBookResponseDto> cancelBook(PatchBookRequestDto dto) {
        return null;
    }

    @Override// 리뷰 작성
    public ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto) {

        try {
//            long reservationId = dto.getReservationId();
//            String reviewContent = dto.getReviewContent();
//            long reviewStarPoint = dto.getReviewStarPoint();

            ReviewEntity reviewEntity = new ReviewEntity(dto);


            this.reviewRepository.save(reviewEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostReviewResponseDto.success();

    }

    @Override // 리뷰 삭제
    public ResponseEntity<? super DeleteReviewResponseDto> deleteReview(long reviewId) {

        ReviewEntity reviewEntity = new ReviewEntity();
        try {
            reviewRepository.findById(reviewId);
            if (reviewId == 0) {
                return DeleteReviewResponseDto.noExistedReview();
            }
                reviewRepository.deleteById(reviewId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();

        }
        return DeleteReviewResponseDto.success();
    }

    @Override // 리뷰 불러오기
    public ResponseEntity<? super GetReviewResponseDto> getReview(GetReviewRequestDto dto) {
        dto.setUserId(dto.getUserId());

        List<GetUserReviewResultSet> resultSets;

        try {
            resultSets = reviewRepository.getReview(dto.getUserId());
            if (resultSets == null) {
                return GetReviewResponseDto.noExistedReview();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetReviewResponseDto.success(resultSets);
    }

    @Override //관심글램핑 불러오기
    public ResponseEntity<? super GetFavoriteGlampingListResponseDto> getFavoriteGlamping
            (GetFavoriteGlampingRequestDto email) {
        return null;
    }

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(GetUserRequestDto email) {
        return null;
    }

    @Override
    public ResponseEntity<? super UpdateUserResponseDto> updateUser(UpdateUserRequestDto email) {
        return null;
    }

    @Override// 회원 탈퇴
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(long userId) {
        UserEntity userEntity = new UserEntity();
        try {
            userRepository.findById(userId);
            if (userId == 0){
                return DeleteUserResponseDto.noExistedUser();
            }
            userRepository.deleteById(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DeleteUserResponseDto.success();
    }
}
