package com.green.glampick.service.implement;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GlampingServiceImpl implements GlampingService {
    private final GlampingMapper mapper;
    private final AuthenticationFacade facade;
// 민지 =================================================================================================================
    @Override
    public ResponseEntity<? super GetSearchGlampingListResponseDto> searchGlamping() {
        return null;
    }

// 강국 =================================================================================================================

    public ResponseEntity<? super GetFavoriteGlampingResponseDto> favoriteGlamping(GetFavoriteRequestDto p) {
        //p.setUserId(facade.getLoginUserId());
        p.setUserId(1);
        System.out.println(p.getGlampId() + "and" + p.getUserId());
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

    public ResponseEntity<? super GetGlampingInformationResponseDto> getInfoGlampingDetail(GetInfoRequestDto p) {
        //글램핑 정보 불러오기
        return null;
    }
    public ResponseEntity<? super GetGlampingReviewInfoResponseDto> getInfoReviewList(ReviewInfoRequestDto p) {
        //리뷰 불러오기
        return null;
    }
}
