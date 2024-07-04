package com.green.glampick.service.implement;

import com.green.glampick.dto.object.ReviewListItem;
import com.green.glampick.dto.object.glamping.GlampingDetailReviewItem;
import com.green.glampick.dto.object.glamping.GlampingListItem;
import com.green.glampick.dto.object.glamping.GlampingRoomListItem;
import com.green.glampick.dto.request.GlampingSearchRequestDto;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.glamping.GetFavoriteRequestDto;
import com.green.glampick.dto.request.glamping.GetInfoRequestDto;
import com.green.glampick.dto.request.glamping.ReviewInfoRequestDto;
import com.green.glampick.dto.response.glamping.GetGlampingInformationResponseDto;
import com.green.glampick.dto.response.glamping.GetGlampingReviewInfoResponseDto;
import com.green.glampick.dto.response.glamping.GetSearchGlampingListResponseDto;
import com.green.glampick.dto.response.glamping.favorite.GetFavoriteGlampingResponseDto;
import com.green.glampick.mapper.GlampingMapper;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.service.GlampingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GlampingServiceImpl implements GlampingService {
    private final GlampingMapper mapper;
    private final AuthenticationFacade facade;
// 민지 =================================================================================================================
    @Override
    public ResponseEntity<? super GetSearchGlampingListResponseDto> searchGlamping(GlampingSearchRequestDto req) {
        // 필요한 데이터가 모두 입력되었는지
        try {
            isNull(req.getRegion());
            isNull(req.getInDate());
            isNull(req.getOutDate());
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            return GetSearchGlampingListResponseDto.validationFailed(msg);
        }

        // 값이 올바르게 들어갔는지
        if(req.getPeople() < 2 || req.getPeople() > 6) {
            return GetSearchGlampingListResponseDto.wrongPersonnel();
        }
        if(checkDate(req.getInDate(), req.getOutDate())){
            return GetSearchGlampingListResponseDto.wrongDate();
        }

//        // 검색어와 일치하는 글램핑장이 존재하나?
//        // existGlamp : 일치하는 글램핑장 pk
//        List<Long> existGlamp = mapper.existGlamp(req.getRegion(), req.getSearchWord());
//        if (existGlamp.size() == 1) {
//            return GetSearchGlampingListResponseDto.existGlamp(existGlamp.get(0));
//        }

        List<GlampingListItem> result = mapper.searchGlampList(req);
        if(result == null || result.isEmpty()) {
            return GetSearchGlampingListResponseDto.isNull();
        }


        int searchCount = 1;

        return GetSearchGlampingListResponseDto.success(searchCount, result);
    }



// 강국 =================================================================================================================
    @Override
    public ResponseEntity<? super GetFavoriteGlampingResponseDto> favoriteGlamping(GetFavoriteRequestDto p) {
        //p.setUserId(facade.getLoginUserId());
        p.setUserId(1);
        try {
            //관심글램픽
            int result = mapper.deleteFavorite(p);
            if (result == 0) {
                // 관심등록
                int insResult = mapper.insertFavorite(p);
                System.out.println(insResult);
                System.out.println("result -> " + result);
                return GetFavoriteGlampingResponseDto.success(result);
            } else {
                System.out.println("result -> " + result);
                return GetFavoriteGlampingResponseDto.success(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }
    @Override
    public ResponseEntity<? super GetGlampingInformationResponseDto> getInfoGlampingDetail(GetInfoRequestDto p) {
        //글램핑 정보 불러오기
        GetGlampingInformationResponseDto glampInfoDto = mapper.selGlampingInfo(p);

        List<GlampingRoomListItem> rooms = mapper.selRoomInfo(p);   // 글램핑 상세페이지 객실 정보 리스트
        List<GlampingDetailReviewItem> reviews = mapper.selReviewInfoInGlamping(p.getGlampId()); // 글램핑 상세페이지 리뷰 리스트
        int userCount = mapper.selCount(p.getGlampId()); // 리뷰 유저수

        // dto 데이터 세팅
        glampInfoDto.setCountReviewUsers(userCount);
        glampInfoDto.setReviewItems(reviews);
        glampInfoDto.setRoomItems(rooms);

        for (GlampingRoomListItem room : rooms) {
            List<String> inputPicsList = mapper.selRoomPics(room.getRoomId());
            room.setRoomPics(inputPicsList);
        }

        return new ResponseEntity<>(glampInfoDto,HttpStatus.OK) ;
    }
    @Override
    public ResponseEntity<? super GetGlampingReviewInfoResponseDto> getInfoReviewList(ReviewInfoRequestDto p) {
        //리뷰 불러오기



//        GetGlampingReviewInfoResponseDto.builder().reviewListItems().build();

        List<ReviewListItem> reviews = mapper.selReviewInfo(p.getGlampId());
//        private List<String> reviewImages;
//        private List<String> roomNames;


        return null;
    }

// 민지 에러체크 =========================================================================================================
    private void isNull (String text) {
        if (text == null || text.isEmpty()) {
            throw new RuntimeException();
        }
    }
    private boolean checkDate (String in, String out) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inDate = LocalDate.parse(in, formatter);
        LocalDate outDate = LocalDate.parse(out, formatter);
        return outDate.isBefore(inDate); // 틀리면 true
    }
}


