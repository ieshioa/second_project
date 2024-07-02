package com.green.glampick.dto.response.main;

import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.object.main.MountainViewGlampingItem;
import com.green.glampick.dto.object.main.PopularGlampingItem;
import com.green.glampick.dto.object.main.WithPetGlampingItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
public class GetMainGlampingListResponseDto extends ResponseDto {

    private List<PopularGlampingItem> popular;
    private List<WithPetGlampingItem> withPet;
    private List<MountainViewGlampingItem> mountainView;

    public GetMainGlampingListResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<ResponseDto> success() {
        ResponseDto result = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
