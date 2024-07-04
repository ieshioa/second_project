package com.green.glampick.service;

import com.green.glampick.dto.request.GlampingSearchRequestDto;
import com.green.glampick.dto.request.glamping.GetFavoriteRequestDto;
import com.green.glampick.dto.request.glamping.GetInfoRequestDto;
import com.green.glampick.dto.request.glamping.ReviewInfoRequestDto;
import com.green.glampick.dto.response.glamping.GetGlampingInformationResponseDto;
import com.green.glampick.dto.response.glamping.GetGlampingReviewInfoResponseDto;
import com.green.glampick.dto.response.glamping.GetSearchGlampingListResponseDto;
import com.green.glampick.dto.response.glamping.favorite.GetFavoriteGlampingResponseDto;
import org.springframework.http.ResponseEntity;

public interface GlampingService {


    ResponseEntity<? super GetSearchGlampingListResponseDto> searchGlamping(GlampingSearchRequestDto searchReq);
    ResponseEntity<? super GetFavoriteGlampingResponseDto> favoriteGlamping(GetFavoriteRequestDto p);
    ResponseEntity<? super GetGlampingInformationResponseDto> getInfoGlampingDetail(GetInfoRequestDto p);

    ResponseEntity<? super GetGlampingReviewInfoResponseDto> getInfoReviewList(ReviewInfoRequestDto p);

}
