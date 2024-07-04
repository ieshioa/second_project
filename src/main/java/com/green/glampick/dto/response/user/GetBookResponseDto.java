package com.green.glampick.dto.response.user;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.entity.GlampingEntity;
import com.green.glampick.entity.ReservationEntity;
import com.green.glampick.entity.RoomEntity;
import com.green.glampick.repository.resultset.GetBookResultSet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
public class GetBookResponseDto extends ResponseDto {

    private String createdAt;
    private long reservationId;
    private String checkInDate;
    private String checkOutDate;
    private String glampName;
    private String roomName;
    private String checkInTime;
    private String checkOutTime;
    private long reservationStatus;



    private GetBookResponseDto(GetBookResultSet resultSet) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.createdAt = resultSet.getCreatedAt();
        this.reservationId = resultSet.getReservationId();
        this.checkInDate = resultSet.getCheckInDate();
        this.checkOutDate = resultSet.getCheckOutDate();
        this.glampName = resultSet.getGlampName();
        this.roomName = resultSet.getRoomName();
        this.checkInTime = resultSet.getCheckInTime();
        this.checkOutTime = resultSet.getCheckOutTime();
        this.reservationStatus = resultSet.getReservationStatus();
    }

    public static ResponseEntity<ResponseDto> success(GetBookResultSet resultSet) {
        GetBookResponseDto result = new GetBookResponseDto(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedBook() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOOK, ResponseMessage.NOT_EXISTED_BOOK);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_PERMISSION, ResponseMessage.NOT_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }

}
