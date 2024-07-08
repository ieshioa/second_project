package com.green.glampick.service.implement;

import com.green.glampick.dto.object.ReviewListItem;
import com.green.glampick.dto.object.glamping.GlampingDetailReviewItem;
import com.green.glampick.dto.object.glamping.GlampingListItem;
import com.green.glampick.dto.object.glamping.GlampingRoomListItem;
import com.green.glampick.dto.request.glamping.GlampingSearchRequestDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GlampingServiceImpl implements GlampingService {
    private final GlampingMapper mapper;
    private final AuthenticationFacade facade;
// 민지 =================================================================================================================
    @Override
    @Transactional
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

        // 검색어와 일치하는 글램핑장이 존재하나?
        // List : 일치하는 글램핑장 pk
        if (req.getSearchWord() != null) {
            List<Long> existGlampLike = mapper.existGlampLike(req.getRegion(), req.getSearchWord());
            if(existGlampLike != null) {  // 지역이 일치하고 검색어가 포함된 것이 하나만 있다면
                List<Long> existGlamp = mapper.existGlamp(req.getRegion(), req.getSearchWord());
                if (existGlamp.size() == 1) {       // 그 하나의 검색어와 완벽히 일치하는 글램핑장이 존재한다면
                    return GetSearchGlampingListResponseDto.existGlamp(existGlamp.get(0));  // 바로 상세보기 페이지로 갈 수 있게 pk를 리턴한다
                }
            }
        }

        List<Integer> filter = req.getFilter();
        if(filter != null) {
            req.setFilterSize(filter.size());
        }

        if(req.getSortType() < 0) {     // default 1
            req.setSortType(1);
        }

        List<GlampingListItem> result = mapper.searchGlampList(req);
        if(result == null || result.isEmpty()) {
            return GetSearchGlampingListResponseDto.isNull();
        }

        int searchCount = mapper.searchCount(req);

        return GetSearchGlampingListResponseDto.success(searchCount, result);
    }



// 강국 =================================================================================================================
    @Override
    @Transactional
    public ResponseEntity<? super GetFavoriteGlampingResponseDto> favoriteGlamping(GetFavoriteRequestDto p) {
        //p.setUserId(facade.getLoginUserId());
        p.setUserId(1);
        try {
            //관심글램픽
            int result = mapper.deleteFavorite(p);
            if (result == 0) {
                // 관심등록
                mapper.insertFavorite(p);
                return GetFavoriteGlampingResponseDto.success(result);
            } else {
                return GetFavoriteGlampingResponseDto.success(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }
    @Override
    public ResponseEntity<? super GetGlampingInformationResponseDto> getInfoGlampingDetail(GetInfoRequestDto p) {

        GetGlampingInformationResponseDto glampInfoDto = mapper.selGlampingInfo(p); //글램핑 정보 불러오기
        List<GlampingRoomListItem> rooms = mapper.selRoomInfo(p);   // 글램핑 상세페이지 객실 정보 리스트
        List<GlampingDetailReviewItem> reviews = mapper.selReviewInfoInGlamping(p.getGlampId()); // 글램핑 상세페이지 리뷰 리스트
        int userCount = mapper.selCount(p.getGlampId()); // 리뷰 유저수
        HashSet<String> hashServices = new HashSet<>();


        // 객실 이미지 & 서비스 가져오기
        for (GlampingRoomListItem item : rooms) {
            item.setRoomServices(mapper.selRoomService(item.getRoomId())); //객실 서비스 세팅

            List<String> roomServices = item.getRoomServices();

            for (String s : roomServices) {
                if (!roomServices.isEmpty()) {
                    hashServices.add(s);
                }
            }

        }

        glampInfoDto.setRoomService(hashServices);
        // dto 데이터 세팅
        glampInfoDto.setCountReviewUsers(userCount);
        glampInfoDto.setReviewItems(reviews);
        glampInfoDto.setRoomItems(rooms);
        return new ResponseEntity<>(glampInfoDto,HttpStatus.OK) ;
    }
    @Override
    public ResponseEntity<? super GetGlampingReviewInfoResponseDto> getInfoReviewList(ReviewInfoRequestDto p) {


        // Data Get
        List<ReviewListItem> reviews = mapper.selReviewInfo(p.getGlampId());
        List<String> roomNameList = mapper.selRoomNames(p.getGlampId());
        List<String> reviewImage = new ArrayList<>();

        //리뷰사진 가져오기
        for (int i = 0; i < reviews.size(); i++) {
            List<String> inputImageList = mapper.selReviewImage(reviews.get(i).getReviewId());
            reviews.get(i).setReviewImages(inputImageList);

            /*
                String imgage = reviews.get(i).getReviewImages().get(i);
                reviewImage.add(imgage);
            */
        }

        //input ResponseDto
        GetGlampingReviewInfoResponseDto dto = GetGlampingReviewInfoResponseDto.builder()
                .reviewListItems(reviews).roomNames(roomNameList).allReviewImage(reviewImage).build();

        return new ResponseEntity<>(dto, HttpStatus.OK);
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


