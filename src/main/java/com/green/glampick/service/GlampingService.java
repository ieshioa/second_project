package com.green.glampick.service;

import com.green.glampick.dto.request.glamping.GetInfoReq;
import com.green.glampick.dto.response.glamping.GetGlampingInformationResponseDto;
import com.green.glampick.dto.response.glamping.GetSearchGlampingListResponseDto;
import com.green.glampick.dto.response.glamping.favorite.GetFavoriteGlampingResponseDto;
import org.springframework.http.ResponseEntity;

public interface GlampingService {

    ResponseEntity<? super GetSearchGlampingListResponseDto> searchGlamping();
    ResponseEntity<? super GetFavoriteGlampingResponseDto> favoriteGlamping(long glampId);
    ResponseEntity<? super GetGlampingInformationResponseDto> getInfoGlampingDetail(GetInfoReq p);

}
