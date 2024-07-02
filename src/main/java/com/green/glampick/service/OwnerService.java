package com.green.glampick.service;

import com.green.glampick.dto.request.GlampingPost;
import com.green.glampick.dto.response.owner.GetOwnerBookListResponseDto;
import com.green.glampick.dto.response.owner.PostGlampingInfoResponseDto;
import org.springframework.http.ResponseEntity;

public interface OwnerService {

    ResponseEntity<? super PostGlampingInfoResponseDto> postGlampingInfo(GlampingPost glampingPostReq);
    ResponseEntity<? super GetOwnerBookListResponseDto> getGlampReservation(long glampId);

}
