package com.green.glampick.dto.response.user;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.repository.resultset.GetReservationBeforeResultSet;
import com.green.glampick.repository.resultset.GetReservationCancelResultSet;
import com.green.glampick.repository.resultset.GetReservationCompleteResultSet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Setter
@Getter
public class GetBookResponseDto extends ResponseDto {

    private List<GetReservationBeforeResultSet> reservationBeforeResultSetList;
    private List<GetReservationCompleteResultSet> reservationCompleteResultSetList;
    private List<GetReservationCancelResultSet> reservationCancelResultSetList;

    private GetBookResponseDto(List<GetReservationBeforeResultSet> reservationBeforeResultSetList
                                , List<GetReservationCompleteResultSet> reservationCompleteResultSetList
                                , List<GetReservationCancelResultSet> reservationCancelResultSetList)
    {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.reservationBeforeResultSetList = reservationBeforeResultSetList;
        this.reservationCancelResultSetList = reservationCancelResultSetList;
        this.reservationCompleteResultSetList = reservationCompleteResultSetList;
    }

    public static ResponseEntity<ResponseDto> success
            (List<GetReservationBeforeResultSet> reservationBeforeResultSetList
            , List<GetReservationCompleteResultSet> reservationCompleteResultSetList
            , List<GetReservationCancelResultSet> reservationCancelResultSetList)
    {
        GetBookResponseDto result
                = new GetBookResponseDto
                (reservationBeforeResultSetList, reservationCompleteResultSetList, reservationCancelResultSetList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> noExistedBook() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOOK, ResponseMessage.NOT_EXISTED_BOOK);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistedUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_PERMISSION, ResponseMessage.NOT_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }

    public static ResponseEntity<ResponseDto> validateUserId() {
        ResponseDto result = new ResponseDto(ResponseCode.CANT_FIND_USER, ResponseMessage.CANT_FIND_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
