package com.green.glampick.service.implement;

import com.green.glampick.common.CustomFileUtils;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.object.ReviewListItem;
import com.green.glampick.dto.object.UserReviewListItem;
import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.user.*;
import com.green.glampick.entity.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ReservationBeforeRepository reservationBeforeRepository;
    private final ReservationCancelRepository reservationCancelRepository;
    private final ReviewRepository reviewRepository;
    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;
    private final FavoriteGlampingRepository favoriteGlampingRepository;
    private final CustomFileUtils customFileUtils;
    private final ReviewImageRepository reviewImageRepository;

    //  마이페이지 - 예약 내역 불러오기  //
    @Override
    public ResponseEntity<? super GetBookResponseDto> getBook(GetBookRequestDto dto) {

        long loggedInUserId = authenticationFacade.getLoginUserId();
        if (loggedInUserId == 0) {
            return GetBookResponseDto.noPermission();
        }
        dto.setUserId(authenticationFacade.getLoginUserId());


        List<GetBookResultSet> resultSets;

        try {
            resultSets = reservationBeforeRepository.getBook(dto.getUserId());
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
        if (loggedInUserId == 0) {
            return CancelBookResponseDto.noPermission();
        }
        dto.setUserId(authenticationFacade.getLoginUserId());


        Optional<ReservationBeforeEntity> optionalBeforeEntity = Optional.empty();

        try {

            optionalBeforeEntity = reservationBeforeRepository.findById(dto.getReservationId());

            // Entity 로 가져온 데이터가 없다면, 존재하지 않는 예약내역에 대한 응답을 반환한다.
            if (optionalBeforeEntity.isEmpty()) {
                return CancelBookResponseDto.noExistedBook();
            }

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
            reservationBeforeRepository.delete(beforeEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CancelBookResponseDto.success();
    }

    //  마이페이지 - 리뷰 작성하기  //
    @Override
    public ResponseEntity<? super PostReviewResponseDto> postReview(PostReviewRequestDto dto, List<MultipartFile> mf) {
        long loggedInUserId = authenticationFacade.getLoginUserId();
        dto.setUserId(loggedInUserId);


            ReviewEntity reviewEntity = new ReviewEntity(dto);
            reviewEntity = reviewRepository.save(reviewEntity);

            PostReviewPicsRequestDto postReviewPicsRequestDto = PostReviewPicsRequestDto.builder().reviewId(reviewEntity.getReviewId()).build();
            // 파일을 저장할 폴더 경로를 생성
            String makefoled = String.format("review/%d/%d", loggedInUserId, reviewEntity.getReviewId());
            // 폴더를 생성
            customFileUtils.makeFolders(makefoled);

        try {
//            List<MultipartFile> reviewImageList = dto.getReviewImageFiles();
            List<ReviewImageEntity> reviewImageEntityList = new ArrayList<>();

            for (MultipartFile image : mf) {
                String saveFileName = customFileUtils.makeRandomFileName(image);
                postReviewPicsRequestDto.getReviewPicsName().add(saveFileName);
                String filePath = String.format("%s/%s", makefoled, saveFileName);
                customFileUtils.transferTo(image, filePath);

                ReviewImageEntity reviewImageEntity = new ReviewImageEntity();
                reviewImageEntity.setReviewId(reviewEntity.getReviewId());
                reviewImageEntity.setReviewImageName(saveFileName);
                reviewImageEntityList.add(reviewImageEntity);
            }
            this.reviewImageRepository.saveAll(reviewImageEntityList);

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

        long loggedInUserId = authenticationFacade.getLoginUserId();
        if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
        dto.setUserId(loggedInUserId);

        List<GetUserReviewResultSet> resultSetList = null;
        List<ReviewImageEntity> imageEntities = new ArrayList<>();

        try {


            resultSetList = reviewRepository.getReview(dto.getUserId());
            List<Long> reviewIds = resultSetList.stream()
                    .map(GetUserReviewResultSet::getReviewId)
                    .collect(Collectors.toList());

            imageEntities = reviewImageRepository.findByReviewIdIn(reviewIds);

            List<UserReviewListItem> reviewListItems = new ArrayList<>();

            for (GetUserReviewResultSet resultSet : resultSetList) {
                UserReviewListItem reviewListItem = new UserReviewListItem();
                reviewListItem.setUserProfileImage(resultSet.getUserProfileImage());
                reviewListItem.setUserNickName(resultSet.getUserNickname());
                reviewListItem.setStarPoint(resultSet.getReviewStarPoint());
                reviewListItem.setCreatedAt(resultSet.getCreatedAt().toString());
                reviewListItem.setUserReviewContent(resultSet.getReviewContent());
                reviewListItem.setOwnerReviewContent(resultSet.getOwnerReviewComment());
                reviewListItem.setGlampName(resultSet.getGlampName());
                reviewListItem.setRoomName(resultSet.getRoomName());

                String basePath = String.format("review/%d/%d", loggedInUserId, resultSet.getReviewId());
                List<String> imageUrls = imageEntities.stream()
                        .filter(entity -> entity.getReviewId() == resultSet.getReviewId())
                        .map(entity -> String.format("%s/%s", basePath, entity.getReviewImageName())) // 경로를 파일명으로 구성
                        .collect(Collectors.toList());
                reviewListItem.setReviewImages(imageUrls);

                reviewListItems.add(reviewListItem);
            }

            return GetReviewResponseDto.success(reviewListItems);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

    }


    @Override //관심글램핑 불러오기
    public ResponseEntity<? super GetFavoriteGlampingResponseDto> getFavoriteGlamping(GetFavoriteGlampingRequestDto dto) {
        dto.setUserId(dto.getUserId());
        List<GetFavoriteGlampingResultSet> resultSets;

        try {
            resultSets = favoriteGlampingRepository.getFavoriteGlamping(dto.getUserId());
            if (resultSets == null) {
                return GetFavoriteGlampingResponseDto.noExistedGlamp();
            }
        } catch (Exception e) {
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
            if (dto.getUserId() == 0) {
                return GetUserResponseDto.noExistedUser();
            }

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
            if (dto.getUserId() == 0) {
                return UpdateUserResponseDto.noExistedUser();
            }

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
            if (userId == 0) {
                return DeleteUserResponseDto.noExistedUser();
            }
            userRepository.deleteById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DeleteUserResponseDto.success();
    }
}
