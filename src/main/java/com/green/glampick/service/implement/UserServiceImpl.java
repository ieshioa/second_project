package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.user.*;
import com.green.glampick.entity.ReservationBeforeEntity;
import com.green.glampick.entity.ReservationCancelEntity;
import com.green.glampick.entity.ReviewEntity;
import com.green.glampick.entity.UserEntity;
import com.green.glampick.repository.ReservationRepository;
import com.green.glampick.repository.ReviewRepository;
import com.green.glampick.repository.UserRepository;
import com.green.glampick.repository.resultset.GetBookResultSet;
import com.green.glampick.repository.ReservationCancelRepository;
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


    @Override
    public ResponseEntity<? super GetBookResponseDto> getBook(GetBookRequestDto dto) {
        long loggedInUserId = authenticationFacade.getLoginUserId();

        if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }

        dto.setUserId(authenticationFacade.getLoginUserId());

        List<GetBookResultSet> resultSets;

        try {

            resultSets = reservationRepository.getBook(dto.getUserId());

            if (resultSets == null) { return GetBookResponseDto.noExistedBook(); }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetBookResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super CancelBookResponseDto> cancelBook(CancelBookRequestDto dto) {
        dto.setUserId(authenticationFacade.getLoginUserId());

        try {

            Optional<ReservationBeforeEntity> optionalBeforeEntity = reservationRepository.findById(dto.getReservationId());

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

    @Override
    public ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto) {

        try {
            ReviewEntity reviewEntity = new ReviewEntity(dto);
            this.reviewRepository.save(reviewEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostReviewResponseDto.success();

    }

    @Override
    public ResponseEntity<? super DeleteReviewResponseDto> deleteReview(int email) {
        return null;
    }

    @Override
    public ResponseEntity<? super GetReviewResponseDto> getReview(GetReviewRequestDto email) {
        return null;
    }

    @Override
    public ResponseEntity<? super GetFavoriteGlampingListResponseDto> getFavoriteGlamping(GetFavoriteGlampingRequestDto email) {
        return null;
    }

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

    @Override
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(int userId) {
        return null;
    }
}
