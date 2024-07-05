package com.green.glampick.mapper;

import com.green.glampick.dto.object.ReviewListItem;
import com.green.glampick.dto.object.glamping.GlampingDetailReviewItem;
import com.green.glampick.dto.object.glamping.GlampingRoomListItem;
import com.green.glampick.dto.request.glamping.GetFavoriteRequestDto;
import com.green.glampick.dto.request.glamping.GetInfoRequestDto;
import com.green.glampick.dto.response.glamping.GetGlampingInformationResponseDto;
import com.green.glampick.dto.object.glamping.GlampingListItem;
import com.green.glampick.dto.request.glamping.GlampingSearchRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GlampingMapper {
    // 민지
    List<GlampingListItem>  searchGlampList(GlampingSearchRequestDto req);
    int searchCount(GlampingSearchRequestDto req);
    List<Long> existGlamp(String region, String searchWord);
    List<Long> existGlampLike(String region, String searchWord);

    // 강국
    // 관심등록
    int deleteFavorite(GetFavoriteRequestDto p);
    int insertFavorite(GetFavoriteRequestDto p);
    //글램핑 상세정보
    GetGlampingInformationResponseDto selGlampingInfo(GetInfoRequestDto p); //글램핑정보
    List<GlampingRoomListItem> selRoomInfo(GetInfoRequestDto p); //객실정보
    List<String> selRoomPics(long roomId);//객실이미지
    List<GlampingDetailReviewItem> selReviewInfoInGlamping(long glampId);//리뷰정보
    int selCount(long glampId);//리뷰유저수
    List<String> selRoomService(long roomId); // 룸서스
    //리뷰
    List<ReviewListItem> selReviewInfo(long glampId); // 리뷰페이지 리뷰정보
    List<String> selReviewImage(long reviewId); // 리뷰 이미지
    List<String> selRoomNames(long glampId); // 글랭핑 보유 객실이름들

}
