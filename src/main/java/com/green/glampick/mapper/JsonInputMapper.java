package com.green.glampick.mapper;

import com.green.glampick.dto.object.room.RoomInfoDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JsonInputMapper {
    Long selGlamp(String glampName);
    int insRoomData(RoomInfoDto roomInfoDto);
}
