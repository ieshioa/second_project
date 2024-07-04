package com.green.glampick.dto.response.glamping;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.dto.ResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetSearchGlampingResponseDto extends ResponseDto {

    @Schema(example = "1", description = "검색된 글램핑 PK")
    private long glampId;

    private GetSearchGlampingResponseDto(long glampId) {
        super(ResponseCode.SUCCESS, "1개의 글램핑을 찾았습니다.");
        this.glampId = glampId;
    }

    public static ResponseEntity<ResponseDto> existGlamp(long glamId) {
        GetSearchGlampingResponseDto result = new GetSearchGlampingResponseDto(glamId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
