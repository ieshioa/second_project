package com.green.glampick.service;

import com.green.glampick.dto.request.owner.GlampingPostRequestDto;
import com.green.glampick.dto.request.ReviewPatchRequestDto;
import com.green.glampick.dto.request.ReviewPostRequestDto;
import com.green.glampick.dto.request.owner.RoomPostRequestDto;
import com.green.glampick.dto.response.owner.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OwnerService {

    ResponseEntity<? super PostGlampingInfoResponseDto> postGlampingInfo(GlampingPostRequestDto glampingPostRequestDtoReq, MultipartFile glampImg);
    ResponseEntity<? super PostRoomInfoResponseDto> postRoomInfo(RoomPostRequestDto req, List<MultipartFile> img);

    ResponseEntity<? super GetOwnerBookListResponseDto> getGlampReservation(long glampId);

    ResponseEntity<? super PostOwnerReviewInfoResponseDto> postReview(ReviewPostRequestDto p);
    ResponseEntity<? super PatchOwnerReviewInfoResponseDto> patchReview(ReviewPatchRequestDto p);
}
