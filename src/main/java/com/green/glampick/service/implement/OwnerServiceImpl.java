package com.green.glampick.service.implement;

import com.green.glampick.common.CustomFileUtils;
import com.green.glampick.dto.object.glamping.RoomItem;
import com.green.glampick.dto.request.GlampingPostRequestDto;
import com.green.glampick.dto.request.ReviewPatchRequestDto;
import com.green.glampick.dto.request.ReviewPostRequestDto;
import com.green.glampick.dto.response.owner.GetOwnerBookListResponseDto;
import com.green.glampick.dto.response.owner.PatchOwnerReviewInfoResponseDto;
import com.green.glampick.dto.response.owner.PostGlampingInfoResponseDto;
import com.green.glampick.dto.response.owner.PostOwnerReviewInfoResponseDto;
import com.green.glampick.mapper.OwnerMapper;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
//        // 유저 PK 불러오기
//      //  req.setUserId(authenticationFacade.getLoginUserId());
//        req.setUserId(1);
//        if(req.getUserId() <= 0) {
//            return PostGlampingInfoResponseDto.validateUserId();
//        }
//
//        // VALIDATION_FAILED
//        try {
//            postValidate(req, glampImg);
//        } catch (Exception e) {
//            String msg = e.getMessage();
//            return PostGlampingInfoResponseDto.validationFailed(msg);
//        }
//
//        // 랜덤 파일명 생성
//        String glmapImgName = customFileUtils.makeRandomFileName(glampImg);
//        req.setGlampingImg(glmapImgName);
//        for (RoomItem roomItem : req.getRoomItems()) {
//            List<String> roomImg = new ArrayList<>();
//            for (MultipartFile img : roomItem.getRoomImg()) {
//                String imgName = customFileUtils.makeRandomFileName(img);
//                roomImg.add(imgName);
//            }
//            roomItem.setRoomImgName(roomImg);
//        }
//
//        // glamping insert 실행
//        try {
//            mapper.postGlamping(req);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return PostGlampingInfoResponseDto.databaseError();
//        }
//
//        try {
//            // 폴더 : /glamping/{glampId}
//            String glampPath = String.format("/glamping/%s", req.getGlampId());
//            customFileUtils.makeFolders(glampPath);
//
//
//            // 파일을 저장한다
//
//
//        } catch (Exception e) {
//            return PostGlampingInfoResponseDto.validateUserId();
//        }
//
//

        return null;
    }

// 강국 =================================================================================================================


// 민지 에러처리 =========================================================================================================
    private void postValidate(GlampingPostRequestDto req
            , MultipartFile glampImg) {

        // 유저가 이미 글램핑장을 가지고 있는가?
//        int hasExistingGlamping = mapper.hasExistingGlamping(req.getUserId()); // 0 = 가진 글램핑장 없음
        int hasExistingGlamping = 0;
        if(hasExistingGlamping != 0) {
            throw new RuntimeException("이미 회원님의 계정에 등록된 글램핑 정보가 있습니다.");
        }

        // 글램핑 위치가 중복되지 않는가?
        int existingLocation = 0;
//        int existingLocation = mapper.existingLocation(req.getGlampLocation()); // 0 = 중복 없음
        if(existingLocation != 0) {
            throw new RuntimeException("이미 같은 위치에 등록된 글램핑장이 존재합니다.");
        }

        // 이미지가 들어가있는가?
        if(glampImg == null || glampImg.isEmpty()) {    // 글램핑 이미지
            throw new RuntimeException("사진을 업로드해주세요.");
        }
        for(RoomItem item : req.getRoomItems()) {       // 객실 이미지
            if(item.getRoomImg().get(0) == null || item.getRoomImg().get(0).isEmpty()) {
                throw new RuntimeException("사진을 업로드해주세요.");
            }
        }

        // 정보가 모두 입력되었는가?
        try {
            postGlampingValidate(req);
        } catch (Exception e) {
            throw new RuntimeException("모든 정보를 입력해주세요.");
        }

        // 바르게 들어갔나?
        List<RoomItem> rooms = req.getRoomItems();
        for(RoomItem room : rooms) {
            if(room.getPrice() <= 0) {
                throw new RuntimeException("객실의 가격 정보가 잘못되었습니다.");
            }
            if(room.getPeopleNum() < MIN_PEOPLE || room.getPeopleNum() > MAX_PEOPLE) {
                throw new RuntimeException("객실의 인원 정보가 잘못되었습니다.");
            }
            if(room.getPeopleMax() < MIN_PEOPLE || room.getPeopleMax() > MAX_PEOPLE) {
                throw new RuntimeException("객실의 인원 정보가 잘못되었습니다.");
            }
            if((room.getPeopleMax() - room.getPeopleNum()) < 0) {
                throw new RuntimeException("최대 인원은 기준 인원보다 작을 수 없습니다.");
            }
        }

        // 파일 크기가 크지 않은가?
        long fileSize = glampImg.getSize();
        for(RoomItem item : req.getRoomItems()) {
            List<MultipartFile> imgs = item.getRoomImg();
            for(MultipartFile img : imgs) {
                fileSize += img.getSize();
            }
        }
        long maxSize = MAX_REQUEST_SIZE * 1024 * 1024;
        if (fileSize > maxSize) {
            throw new RuntimeException("파일의 최대 업로드 가능 크기는 150MB 입니다.");
        }

    }

    private void isNull (String text) {
        if(text == null || text.isEmpty()){
            throw new RuntimeException();
        }
    }

    private void postGlampingValidate(GlampingPostRequestDto req) {
        isNull(req.getGlampName());
        isNull(req.getGlampLocation());
        isNull(req.getRegion());
        isNull(req.getIntro());
        isNull(req.getBasic());
        isNull(req.getParking());
        isNull(req.getNotice());
        //--
        List<RoomItem> rooms = req.getRoomItems();
        for(RoomItem room : rooms) {
            isNull(room.getRoomName());
            isNull(room.getInTime());
            isNull(room.getOutTime());
        }
    }


    @Override
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

}
