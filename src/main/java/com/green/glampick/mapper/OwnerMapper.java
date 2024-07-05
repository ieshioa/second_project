package com.green.glampick.mapper;

import com.green.glampick.dto.request.owner.GlampingPostRequestDto;
import com.green.glampick.dto.request.owner.RoomPostRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnerMapper {
    // 민지
    void insertGlamping(GlampingPostRequestDto req);
    void insertRoom(RoomPostRequestDto req);
    void insertRoomImg (RoomPostRequestDto req);
    void insertRoomService(RoomPostRequestDto req);
    Long hasExistingGlamping(long userId);
    Long existingLocation(String location);
    // 강국
}
