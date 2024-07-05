package com.green.glampick.mapper;

import com.green.glampick.dto.object.glamping.RoomItem;
import com.green.glampick.dto.request.GlampingPostRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnerMapper {
    // 민지
    void insertGlamping(GlampingPostRequestDto req);
    void insertRoom(RoomItem req);
    void insertRoomImg (RoomItem req);
    void insertRoomService(RoomItem req);
    // 강국
}
