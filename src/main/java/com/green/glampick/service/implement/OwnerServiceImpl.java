package com.green.glampick.service.implement;

import com.green.glampick.dto.request.GlampingPost;
import com.green.glampick.dto.response.owner.GetOwnerBookListResponseDto;
import com.green.glampick.dto.response.owner.PostGlampingInfoResponseDto;
import com.green.glampick.mapper.OwnerMapper;
import com.green.glampick.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerMapper mapper;

// 민지 =================================================================================================================
    @Override
    public ResponseEntity<? super PostGlampingInfoResponseDto> postGlampingInfo(GlampingPost req) {
//        if(req.)
//        try {
//            mapper.postGlamping(req);
//        } catch (Exception e) {
//            return PostGlampingInfoResponseDto.databaseError();
//        }
        return null;

    }

    @Override
    public ResponseEntity<? super GetOwnerBookListResponseDto> getGlampReservation(long glampId) {
        return null;
    }

// 강국 =================================================================================================================

}
