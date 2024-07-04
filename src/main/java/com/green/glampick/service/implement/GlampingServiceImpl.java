package com.green.glampick.service.implement;

import com.green.glampick.dto.object.glamping.GlampingListItem;
import com.green.glampick.dto.request.GlampingSearchRequestDto;
import com.green.glampick.dto.request.glamping.GetInfoRequestDto;
import com.green.glampick.dto.request.glamping.ReviewInfoRequestDto;
import com.green.glampick.dto.response.glamping.GetGlampingInformationResponseDto;
import com.green.glampick.dto.response.glamping.GetGlampingReviewInfoResponseDto;
import com.green.glampick.dto.response.glamping.GetSearchGlampingListResponseDto;
import com.green.glampick.dto.response.glamping.favorite.GetFavoriteGlampingResponseDto;
import com.green.glampick.mapper.GlampingMapper;
import com.green.glampick.service.GlampingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.green.glampick.common.GlobalConst.SEARCH_PAGING_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class GlampingServiceImpl implements GlampingService {
    private final GlampingMapper mapper;

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
    public ResponseEntity<? super GetFavoriteGlampingResponseDto> favoriteGlamping(long glampId) {
        return null;
    }

    public ResponseEntity<? super GetGlampingInformationResponseDto> getInfoGlampingDetail(GetInfoRequestDto p) {
        return null;
    }

    public ResponseEntity<? super GetGlampingReviewInfoResponseDto> getInfoReviewList(ReviewInfoRequestDto p) {
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


