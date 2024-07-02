package com.green.glampick.service;

import com.green.glampick.dto.request.GlampingPostRequestDto;
import com.green.glampick.dto.response.owner.GetOwnerBookListResponseDto;
import com.green.glampick.dto.response.owner.PostGlampingInfoResponseDto;
import org.springframework.http.ResponseEntity;

public interface OwnerService {

    ResponseEntity<? super PostGlampingInfoResponseDto> postGlampingInfo(GlampingPostRequestDto glampingPostRequestDtoReq);
    ResponseEntity<? super GetOwnerBookListResponseDto> getGlampReservation(long glampId);

}
