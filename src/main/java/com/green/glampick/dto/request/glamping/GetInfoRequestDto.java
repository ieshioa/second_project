package com.green.glampick.dto.request.glamping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.glampick.common.GlobalConst;
import com.green.glampick.common.Paging;
import lombok.*;

import static com.green.glampick.common.GlobalConst.PAGING_SIZE;

@Getter
@Setter
@ToString
public class GetInfoRequestDto {
    private long glampId;
    private int status;
    @JsonIgnore
    private int size;

    public GetInfoRequestDto(int status) {
        if (status == 0) {
            size = PAGING_SIZE;
        } else if(status == 1){
            size = 0;
        }
    }

}