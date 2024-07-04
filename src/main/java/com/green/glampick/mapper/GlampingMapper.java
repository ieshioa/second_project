package com.green.glampick.mapper;

import com.green.glampick.dto.request.glamping.GetFavoriteRequestDto;
import com.green.glampick.dto.object.glamping.GlampingListItem;
import com.green.glampick.dto.request.GlampingSearchRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GlampingMapper {
    // 민지
    List<GlampingListItem>  searchGlampList(GlampingSearchRequestDto req);
    List<Long> existGlamp(String region, String searchWord);
    // 강국
    int deleteFavorite(GetFavoriteRequestDto p);

    int insertFavorite(GetFavoriteRequestDto p);
}
