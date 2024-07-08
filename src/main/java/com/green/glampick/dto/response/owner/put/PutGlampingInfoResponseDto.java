package com.green.glampick.dto.response.owner.put;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class PutGlampingInfoResponseDto extends ResponseDto {


    private PutGlampingInfoResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    // 성공 - 수정됨
    public static ResponseEntity<ResponseDto> success() {
        ResponseDto result = new ResponseDto(ResponseCode.SUCCESS, "수정을 완료하였습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 성공 - 수정된 내용 없음 (입력된 정보가 기존과 일치)
    public static ResponseEntity<ResponseDto> noAffectedRow() {
        ResponseDto result = new ResponseDto(ResponseCode.SUCCESS, "수정된 내용이 없습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 유저 정보 불러오기 실패 (authenticationFacade)
    public static ResponseEntity<ResponseDto> validateUserId() {
        ResponseDto result = new ResponseDto(ResponseCode.CANT_FIND_USER, ResponseMessage.CANT_FIND_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    // 글램핑 PK 잘못됨
    public static ResponseEntity<ResponseDto> validateGlampId() {
        ResponseDto result = new ResponseDto(ResponseCode.VALIDATION_FAILED, "글램핑 PK 입력이 잘못되었습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    // 정보가 덜 입력됨
    public static ResponseEntity<ResponseDto> validationFailed(String errorMsg) {
        ResponseDto result = new ResponseDto(ResponseCode.VALIDATION_FAILED, errorMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
