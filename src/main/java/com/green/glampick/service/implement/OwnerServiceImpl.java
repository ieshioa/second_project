package com.green.glampick.service.implement;

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

import java.util.ArrayList;
import java.util.List;

import static com.green.glampick.common.GlobalConst.MAX_PEOPLE;
import static com.green.glampick.common.GlobalConst.STANDARD_PEOPLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerMapper mapper;
    private final AuthenticationFacade authenticationFacade;

// 민지 =================================================================================================================

    @Override
    public ResponseEntity<? super PostGlampingInfoResponseDto> postGlampingInfo(GlampingPostRequestDto req) {
        // 유저 PK 불러오기
      //  req.setUserId(authenticationFacade.getLoginUserId());
        req.setUserId(1);
        if(req.getUserId() <= 0) {
            return PostGlampingInfoResponseDto.validateUserId();
        }

        // 정보가 모두 입력되었는가?
        try {
            postGlampingValidate(req);
        } catch (Exception e) {
            return PostGlampingInfoResponseDto.isNull();
        }


        // 유저가 이미 글램핑장을 가지고 있는가?
        //int hasExistingGlamping = mapper.hasExistingGlamping(req.getUserId()); // 0 = 가진 글램핑장 없음
        int hasExistingGlamping = 0;
        if(hasExistingGlamping != 0) {
            return PostGlampingInfoResponseDto.hasExistingGlamping();
        }

        // 글램핑 위치가 중복되지 않는가
        int existingLocation = 0;
//        int existingLocation = mapper.existingLocation(req.getGlampLocation()); // 0 = 중복 없음
        if(existingLocation != 0) {
            return PostGlampingInfoResponseDto.existingLocation();
        }

        // 바르게 들어갔나
        List<RoomItem> rooms = req.getRoomItems();
        for(RoomItem room : rooms) {
            if(room.getPrice() <= 0) {
                return PostGlampingInfoResponseDto.badPrice();
            }
            if(room.getPeopleNum() < STANDARD_PEOPLE || room.getPeopleNum() > MAX_PEOPLE) {
                return PostGlampingInfoResponseDto.badPeople();
            }
            if(room.getPeopleMax() < STANDARD_PEOPLE || room.getPeopleMax() > MAX_PEOPLE) {
                return PostGlampingInfoResponseDto.badPeople();
            }
            if((room.getPeopleMax() - room.getPeopleNum()) < 0) {
                return PostGlampingInfoResponseDto.badPeople();
            }
        }

//        try {
//            mapper.postGlamping(req);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return PostGlampingInfoResponseDto.databaseError();
//        }
        return null;
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
        isNull(req.getGlampingImg());
        //--
        List<RoomItem> rooms = req.getRoomItems();
        for(RoomItem room : rooms) {
            isNull(room.getRoomName());
            isNull(room.getInTime());
            isNull(room.getOutTime());
            isNull(room.getRoomImg().get(0));
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
