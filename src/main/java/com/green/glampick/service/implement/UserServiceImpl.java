package com.green.glampick.service.implement;

import com.green.glampick.common.CustomFileUtils;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.object.UserReviewListItem;
import com.green.glampick.dto.request.user.*;
import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.user.*;
import com.green.glampick.entity.*;
import com.green.glampick.repository.*;
import com.green.glampick.repository.resultset.*;
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
    private final ReservationCompleteRepository reservationCompleteRepository;
    private final ReviewRepository reviewRepository;
    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;
    private final FavoriteGlampingRepository favoriteGlampingRepository;
    private final CustomFileUtils customFileUtils;
    private final ReviewImageRepository reviewImageRepository;


    //  마이페이지 - 예약 내역 불러오기  //
    @Override
    public ResponseEntity<? super GetBookResponseDto> getBook(GetBookRequestDto dto) {

        try{
            long loggedInUserId = authenticationFacade.getLoginUserId();
            if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
            dto.setUserId(loggedInUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return GetUserResponseDto.authorizationFailed();
        }


        List<GetReservationBeforeResultSet> reservationBeforeResultSetList;
        List<GetReservationCancelResultSet> reservationCancelResultSetList;
        List<GetReservationCompleteResultSet> reservationCompleteResultSetList;

        try {

            reservationBeforeResultSetList = reservationBeforeRepository.getBook(dto.getUserId());
            reservationCancelResultSetList = reservationCancelRepository.getBook(dto.getUserId());
            reservationCompleteResultSetList = reservationCompleteRepository.getBook(dto.getUserId());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBookResponseDto.success(reservationBeforeResultSetList
                                         , reservationCompleteResultSetList
                                         , reservationCancelResultSetList);
    }

    //  마이페이지 - 예약 취소하기  //
    @Override
    public ResponseEntity<? super CancelBookResponseDto> cancelBook(CancelBookRequestDto dto) {

        try{
            long loggedInUserId = authenticationFacade.getLoginUserId();
            if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
            dto.setUserId(loggedInUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return GetUserResponseDto.authorizationFailed();
        }


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
                    , beforeEntity.getPayAmount()
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
            long loggedInUserId = 0;
            try{
                loggedInUserId = authenticationFacade.getLoginUserId();
                if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
                dto.setUserId(loggedInUserId);
            } catch (Exception e) {
                e.printStackTrace();
                return GetUserResponseDto.authorizationFailed();
            }


            ReviewEntity reviewEntity = new ReviewEntity(dto);
            reviewEntity = reviewRepository.save(reviewEntity);

            PostReviewPicsRequestDto postReviewPicsRequestDto = PostReviewPicsRequestDto.builder().reviewId(reviewEntity.getReviewId()).build();
            // 파일을 저장할 폴더 경로를 생성
            String makefolder = String.format("review/%d/%d", loggedInUserId, reviewEntity.getReviewId());
            // 폴더를 생성
            customFileUtils.makeFolders(makefolder);

        try {
//            List<MultipartFile> reviewImageList = dto.getReviewImageFiles();
            List<ReviewImageEntity> reviewImageEntityList = new ArrayList<>();

            for (MultipartFile image : mf) {
                String saveFileName = customFileUtils.makeRandomFileName(image);
                postReviewPicsRequestDto.getReviewPicsName().add(saveFileName);
                String filePath = String.format("%s/%s", makefolder, saveFileName);
                customFileUtils.transferTo(image, filePath);

                ReviewImageEntity reviewImageEntity = new ReviewImageEntity();
                reviewImageEntity.setReviewId(reviewEntity.getReviewId());
                reviewImageEntity.setReviewImageName(saveFileName);
                reviewImageEntityList.add(reviewImageEntity);
            }
            this.reviewImageRepository.saveAll(reviewImageEntityList);
            reviewEntity.setReviewStarPoint(dto.getReviewStarPoint());
    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostReviewResponseDto.success();

    }

    @Override // 리뷰 삭제
    public ResponseEntity<? super DeleteReviewResponseDto> deleteReview(DeleteReviewRequestDto dto) {
        long loggedInUserId = authenticationFacade.getLoginUserId();
        dto.setUserId(loggedInUserId);

        ReviewEntity reviewEntity = new ReviewEntity();
        try {
            reviewRepository.findById(dto.getReviewId());
            if (dto.getReviewId() == 0) {
                return DeleteReviewResponseDto.noExistedReview();
            }
            reviewRepository.deleteById(dto.getReviewId());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();

        }
        return DeleteReviewResponseDto.success();
    }

    @Override // 리뷰 불러오기
    public ResponseEntity<? super GetReviewResponseDto> getReview(GetReviewRequestDto dto) {

        try{
            long loggedInUserId = authenticationFacade.getLoginUserId();
            if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
            dto.setUserId(loggedInUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return GetUserResponseDto.authorizationFailed();
        }

        List<GetUserReviewResultSet> resultSetList = null;
        List<ReviewImageEntity> imageEntities = new ArrayList<>();

        try {
            int limit = dto.getLimit();
            int offset = dto.getOffset();

            resultSetList = reviewRepository.getReview(dto.getUserId(), limit, offset);
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

                List<String> imageUrls = imageEntities.stream()
                        .filter(entity -> entity.getReviewId() == resultSet.getReviewId())
                        .map(ReviewImageEntity::getReviewImageName) // 경로를 파일명으로 구성
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

        try{
            long loggedInUserId = authenticationFacade.getLoginUserId();
            if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
            dto.setUserId(loggedInUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return GetUserResponseDto.authorizationFailed();
        }

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

        try{
            long loggedInUserId = authenticationFacade.getLoginUserId();
            if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
            dto.setUserId(loggedInUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return GetUserResponseDto.authorizationFailed();
        }

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
    public ResponseEntity<? super UpdateUserResponseDto> updateUser(UpdateUserRequestDto dto, MultipartFile mf) {
        try{
            long loggedInUserId = authenticationFacade.getLoginUserId();
            if (loggedInUserId == 0) { return GetBookResponseDto.noPermission(); }
            dto.setUserId(loggedInUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return GetUserResponseDto.authorizationFailed();
        }

        try {
            UserEntity userEntity = userRepository.findById(dto.getUserId()).get();
            if (dto.getUserId() == 0) {
                return UpdateUserResponseDto.noExistedUser();
            }

            String path = String.format("user/%d", userEntity.getUserId());
            customFileUtils.deleteFolder(path);
            customFileUtils.makeFolders(path);
            String saveFileName = customFileUtils.makeRandomFileName(mf);
            String filePath = String.format("%s/%s", path, saveFileName);
            customFileUtils.transferTo(mf, filePath);

            String userPw = dto.getUserPw();
            String encodingPw = passwordEncoder.encode(userPw);
            dto.setUserPw(encodingPw);

            userEntity.setUserProfileImage(saveFileName);
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
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(DeleteUserRequestDto dto) {
        long loggedInUserId = authenticationFacade.getLoginUserId();
        dto.setUserId(loggedInUserId);
//        UserEntity userEntity = userRepository.findByUserId(dto.getUserId());
        try {
            userRepository.findById(dto.getUserId());
            if (dto.getUserId() == 0) {
                return DeleteUserResponseDto.noExistedUser();
            }
//            String userPw = dto.getUserPw();
//            String encodingPw = userEntity.getUserPw();
//            boolean matches = passwordEncoder.matches(userPw, encodingPw);
//            if (!matches) { return DeleteUserResponseDto.noPermission(); }
            userRepository.deleteById(dto.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DeleteUserResponseDto.success();
    }
}



