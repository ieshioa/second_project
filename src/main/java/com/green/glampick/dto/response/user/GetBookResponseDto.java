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

import java.util.List;

@Setter
@Getter
public class GetBookResponseDto extends ResponseDto {

    private List<GetBookResultSet> booklist;

    private GetBookResponseDto(List<GetBookResultSet> booklist) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.booklist = booklist;
    }

    public static ResponseEntity<ResponseDto> success(List<GetBookResultSet> booklist) {
        GetBookResponseDto result = new GetBookResponseDto(booklist);
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

}
