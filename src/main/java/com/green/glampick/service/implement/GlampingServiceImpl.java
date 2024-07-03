package com.green.glampick.service.implement;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class GlampingServiceImpl implements GlampingService {
    private final GlampingMapper mapper;

// 민지 =================================================================================================================
    @Override
    public ResponseEntity<? super GetSearchGlampingListResponseDto> searchGlamping() {
        return null;
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
}
