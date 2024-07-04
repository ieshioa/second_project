package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.user.*;
import com.green.glampick.entity.ReservationEntity;
import com.green.glampick.entity.ReviewEntity;
import com.green.glampick.entity.UserEntity;
import com.green.glampick.repository.ReservationRepository;
import com.green.glampick.repository.ReviewRepository;
import com.green.glampick.repository.resultset.GetBookResultSet;
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



    @Override
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

    @Override
    public ResponseEntity<? super PatchBookResponseDto> cancelBook(PatchBookRequestDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto) {

        try {
            long reservationId = dto.getReservationId();
            String reviewContent = dto.getReviewContent();
            long reviewStarPoint = dto.getReviewStarPoint();

            ReviewEntity reviewEntity = new ReviewEntity(dto);


            this.reviewRepository.save(reviewEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostReviewResponseDto.success();

    }

    @Override
    public ResponseEntity<? super DeleteReviewResponseDto> deleteReview(long reviewId) {

        ReviewEntity reviewEntity = new ReviewEntity();
        try {
            reviewRepository.findById(reviewId);
            if (reviewId == 0) {
                return DeleteReviewResponseDto.noExistedReview();
            }else if (reviewId == reviewId){
                reviewRepository.deleteById(reviewId);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();

        }
        return DeleteReviewResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetReviewResponseDto> getReview(GetReviewRequestDto email) {
        return null;
    }

    @Override
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

    @Override
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(int userId) {
        return null;
    }
}
