package com.green.glampick.mapper;

import com.green.glampick.dto.object.owner.BookBeforeItem;
import com.green.glampick.dto.object.owner.BookCancelItem;
import com.green.glampick.dto.object.owner.BookCompleteItem;
import com.green.glampick.dto.request.owner.GlampingPostRequestDto;
import com.green.glampick.dto.request.owner.RoomPostRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OwnerMapper {
    // 민지
    void insertGlamping(GlampingPostRequestDto req);
    Long hasGlamping(long userId);
    Long existingLocation(String location);

    void insertRoom(RoomPostRequestDto req);
    void insertRoomImg(RoomPostRequestDto req);
    void insertRoomService(RoomPostRequestDto req);

    void updateGlampingInfo(GlampingPostRequestDto req);

    void updateRoomInfo(RoomPostRequestDto req);
    List<Integer> selService(long roomId);
    void delService(long roomId);
    void insertRoomService(long roomId, List<Integer> serviceList);

    List<BookBeforeItem> getBookBefore(long glampId);
    List<BookCompleteItem> getBookComplete(long glampId);
    List<BookCancelItem> getBookCancel(long glampId);

    Long getUserIdByGlampId(long glampId);
    // 강국
}
