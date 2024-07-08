package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.user.*;
import com.green.glampick.entity.ReservationBeforeEntity;
import com.green.glampick.entity.ReservationCancelEntity;
import com.green.glampick.entity.ReviewEntity;
import com.green.glampick.entity.UserEntity;
import com.green.glampick.repository.*;
import com.green.glampick.repository.resultset.GetBookResultSet;
import com.green.glampick.repository.resultset.GetFavoriteGlampingResultSet;
import com.green.glampick.repository.resultset.GetUserReviewResultSet;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationCancelRepository reservationCancelRepository;
    private final ReviewRepository reviewRepository;
    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;
    private final FavoriteGlampingRepository favoriteGlampingRepository;

    //  마이페이지 - 예약 내역 불러오기  //
    @Override
    public ResponseEntity<? super GetBookResponseDto> getBook(GetBookRequestDto dto) {

        long loggedInUserId = authenticationFacade.getLoginUserId();
        if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
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

    //  마이페이지 - 예약 취소하기  //
    @Override
    public ResponseEntity<? super CancelBookResponseDto> cancelBook(CancelBookRequestDto dto) {

        long loggedInUserId = authenticationFacade.getLoginUserId();
        if (loggedInUserId == 0) { return CancelBookResponseDto.noPermission(); }
        dto.setUserId(authenticationFacade.getLoginUserId());


        Optional<ReservationBeforeEntity> optionalBeforeEntity = Optional.empty();

        try {

            optionalBeforeEntity = reservationRepository.findById(dto.getReservationId());

            // Entity 로 가져온 데이터가 없다면, 존재하지 않는 예약내역에 대한 응답을 반환한다.
            if (optionalBeforeEntity.isEmpty()) { return CancelBookResponseDto.noExistedBook(); }

            ReservationBeforeEntity beforeEntity = optionalBeforeEntity.get();
            ReservationCancelEntity cancelEntity = new ReservationCancelEntity(
                    beforeEntity.getUserId()
                    , beforeEntity.getGlampId()
                    , beforeEntity.getRoomId()
                    , beforeEntity.getInputName()
                    , beforeEntity.getCheckInDate()
                    , beforeEntity.getCheckOutDate()
                    , beforeEntity.getReservationAmount()
                    , dto.getComment()
                    , beforeEntity.getCreatedAt());

            reservationCancelRepository.save(cancelEntity);
            reservationRepository.delete(beforeEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CancelBookResponseDto.success();
    }

    //  마이페이지 - 리뷰 작성하기  //
    @Override
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
    public ResponseEntity<? super GetFavoriteGlampingResponseDto> getFavoriteGlamping(GetFavoriteGlampingRequestDto dto) {
        dto.setUserId(dto.getUserId());
        List<GetFavoriteGlampingResultSet> resultSets;

        try {
            resultSets = favoriteGlampingRepository.getFavoriteGlamping(dto.getUserId());
            if(resultSets == null){
                return GetFavoriteGlampingResponseDto.noExistedGlamp();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetFavoriteGlampingResponseDto.success(resultSets);
    }

    //  마이페이지 - 내 정보 불러오기  //
    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(GetUserRequestDto dto) {

        dto.setUserId(authenticationFacade.getLoginUserId());

        try {

            UserEntity userEntity = userRepository.findById(dto.getUserId()).get();
            if (dto.getUserId() == 0) { return GetUserResponseDto.noExistedUser(); }

            return GetUserResponseDto.success(userEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

    }

    //  마이페이지 - 내 정보 수정하기  //
    @Override
    public ResponseEntity<? super UpdateUserResponseDto> updateUser(UpdateUserRequestDto dto) {
        dto.setUserId(authenticationFacade.getLoginUserId());

        try {
            UserEntity userEntity = userRepository.findById(dto.getUserId()).get();
            if (dto.getUserId() == 0) { return UpdateUserResponseDto.noExistedUser();}

            String userPw = dto.getUserPw();
            String encodingPw = passwordEncoder.encode(userPw);
            dto.setUserPw(encodingPw);

            userEntity.setUserNickname(dto.getUserNickname());
            userEntity.setUserPw(dto.getUserPw());

            userRepository.save(userEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return UpdateUserResponseDto.success();
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
