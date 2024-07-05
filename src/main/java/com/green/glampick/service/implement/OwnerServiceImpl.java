package com.green.glampick.service.implement;

import com.green.glampick.common.CustomFileUtils;
import com.green.glampick.dto.request.owner.GlampingPostRequestDto;
import com.green.glampick.dto.request.ReviewPatchRequestDto;
import com.green.glampick.dto.request.ReviewPostRequestDto;
import com.green.glampick.dto.request.owner.RoomPostRequestDto;
import com.green.glampick.dto.response.owner.*;
import com.green.glampick.mapper.OwnerMapper;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.green.glampick.common.GlobalConst.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerMapper mapper;
    private final AuthenticationFacade authenticationFacade;
    private final CustomFileUtils customFileUtils;

// 민지 =================================================================================================================

    @Override
    @Transactional
    public ResponseEntity<? super PostGlampingInfoResponseDto> postGlampingInfo(GlampingPostRequestDto req
                        , MultipartFile glampImg) {
        // 유저 PK 불러오기
        req.setUserId(authenticationFacade.getLoginUserId());
     //   req.setUserId(8);
        if(req.getUserId() <= 0) {
            return PostGlampingInfoResponseDto.validateUserId();
        }

        // VALIDATION_FAILED
        try {
            postGlampValidate(req, glampImg);
        } catch (Exception e) {
            String msg = e.getMessage();
            return PostGlampingInfoResponseDto.validationFailed(msg);
        }

        // 이미지 파일명 만들기
        String glmapImgName = customFileUtils.makeRandomFileName(glampImg);
        req.setGlampingImg(glmapImgName);
        // glamping insert 실행
        try {
            mapper.insertGlamping(req);
        } catch (Exception e) {
            e.printStackTrace();
            return PostGlampingInfoResponseDto.databaseError();
        }
        long glampId = req.getGlampId();

        // 글램핑 대표 이미지 넣기
        try {
            // 폴더 : /glamping/{glampId}
            String glampPath = String.format("/glamping/%s/glamp", glampId);
            customFileUtils.makeFolders(glampPath);
            // 파일을 저장한다
            String target = String.format("/%s/%s", glampPath, glmapImgName);
            customFileUtils.transferTo(glampImg, target);
        } catch (Exception e) {
            e.printStackTrace();
            return PostGlampingInfoResponseDto.fileUploadError();
        }

        return PostGlampingInfoResponseDto.success(glampId);
    }

    @Override
    @Transactional
    public ResponseEntity<? super PostRoomInfoResponseDto> postRoomInfo(RoomPostRequestDto req
            , List<MultipartFile> img) {
        // 모든 정보가 올바른가?
        try {
            postRoomValidate(req, img);
        } catch (Exception e) {
            String msg = e.getMessage();
            return PostRoomInfoResponseDto.validationFailed(msg);
        }

        long glampId = req.getGlampId();
        req.setGlampId(glampId);
        mapper.insertRoom(req);  // room 테이블 insert

        long roomId = req.getRoomId();

        // 폴더 만들기
        String roomPath = String.format("/glamping/%s/room/%s", glampId, roomId);
        customFileUtils.makeFolders(roomPath);
        // room 파일명 생성 및 저장
        try {
            List<String> roomImg = new ArrayList<>();
            for (MultipartFile image : img) {
                String imgName = customFileUtils.makeRandomFileName(image);
                roomImg.add(imgName);
                String target = String.format("%s/%s", roomPath, imgName);
                customFileUtils.transferTo(image, target);
            }
            req.setRoomImgName(roomImg);
        } catch (Exception e) {
            e.printStackTrace();
            return PostRoomInfoResponseDto.fileUploadError();
        }
        // 룸 이미지 / 룸 서비스 insert
        try {
            mapper.insertRoomImg(req);
            if(req.getServiceList() != null) {
                mapper.insertRoomService(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return PostRoomInfoResponseDto.databaseError();
        }

        return PostRoomInfoResponseDto.success(roomId);

    }

    public ResponseEntity<? super GetOwnerBookListResponseDto> getGlampReservation(long glampId) {
        return null;
    }


// 강국 =================================================================================================================
    @Override
    public ResponseEntity<? super PostOwnerReviewInfoResponseDto> postReview(ReviewPostRequestDto p) {
        return null;
    }

    @Override
    public ResponseEntity<? super PatchOwnerReviewInfoResponseDto> patchReview(ReviewPatchRequestDto p) {
        return null;
    }



// 민지 에러처리 =========================================================================================================
    private void postGlampValidate(GlampingPostRequestDto req
            , MultipartFile glampImg) {

        // 유저가 이미 글램핑장을 가지고 있는가?
        Long hasExistingGlamping = mapper.hasExistingGlamping(req.getUserId()); // 0 = 가진 글램핑장 없음
        if(hasExistingGlamping != null && hasExistingGlamping != 0) {
            throw new RuntimeException("이미 회원님의 계정에 등록된 글램핑 정보가 있습니다.");
        }

        // 글램핑 위치가 중복되지 않는가?
        Long existingLocation = mapper.existingLocation(req.getGlampLocation()); // 0 = 중복 없음
        if(existingLocation != null && existingLocation != 0) {
            throw new RuntimeException("이미 같은 위치에 등록된 글램핑장이 존재합니다.");
        }

        // 이미지가 들어가있는가?
        if(glampImg == null || glampImg.isEmpty()) {    // 글램핑 이미지
            throw new RuntimeException("사진을 업로드해주세요.");
        }

        // 정보가 모두 입력되었는가?
        glampingPostNullCheck(req);
    }

    private void postRoomValidate(RoomPostRequestDto req, List<MultipartFile> img) {
        // 이미지가 들어가있는가?
        if(img == null || img.isEmpty()) {    // 글램핑 이미지
            throw new RuntimeException("사진을 업로드해주세요.");
        }

        // 정보가 모두 입력되었는가?
        try {
            roomPostNullCheck(req);
        } catch (Exception e) {
            String msg = e.getMessage();
            throw new RuntimeException(msg);
        }

        // 바르게 들어갔나?
        if(req.getPrice() <= 0) {
            throw new RuntimeException("객실의 가격 정보가 잘못되었습니다.");
        }
        if(req.getPeopleNum() < MIN_PEOPLE || req.getPeopleNum() > MAX_PEOPLE) {
            throw new RuntimeException("객실의 인원 정보가 잘못되었습니다.");
        }
        if(req.getPeopleMax() < MIN_PEOPLE || req.getPeopleMax() > MAX_PEOPLE) {
            throw new RuntimeException("객실의 인원 정보가 잘못되었습니다.");
        }
        if((req.getPeopleMax() - req.getPeopleNum()) < 0) {
            throw new RuntimeException("최대 인원은 기준 인원보다 작을 수 없습니다.");
        }
    }


    private void isNull (String text) {
        if(text == null || text.isEmpty()){
            throw new RuntimeException("모든 정보를 입력해주세요.");
        }
    }
    private void isNull (int value) {
        if(value <= 0 ){
            throw new RuntimeException("모든 정보를 입력해주세요.");
        }
    }
    private void isNull (long value) {
        if(value <= 0 ){
            throw new RuntimeException("모든 정보를 입력해주세요.");
        }
    }
    private void glampingPostNullCheck(GlampingPostRequestDto req) {
        isNull(req.getGlampName());
        isNull(req.getGlampLocation());
        isNull(req.getRegion());
        isNull(req.getIntro());
        isNull(req.getBasic());
        isNull(req.getParking());
        isNull(req.getNotice());
    }
    private void roomPostNullCheck(RoomPostRequestDto req) {
        isNull(req.getRoomName());
        isNull(req.getInTime());
        isNull(req.getOutTime());
        isNull(req.getGlampId());
        isNull(req.getPrice());
        isNull(req.getPeopleNum());
        isNull(req.getPeopleMax());
    }

}
