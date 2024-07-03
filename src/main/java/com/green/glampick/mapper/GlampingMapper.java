package com.green.glampick.mapper;

import com.green.glampick.dto.request.glamping.GetFavoriteRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GlampingMapper {
    // 민지

    // 강국
    int deleteFavorite(GetFavoriteRequestDto p);

    int insertFavorite(GetFavoriteRequestDto p);
}
