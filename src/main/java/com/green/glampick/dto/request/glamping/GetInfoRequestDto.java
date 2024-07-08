package com.green.glampick.dto.request.glamping;

import com.green.glampick.common.Paging;
import lombok.*;

import static com.green.glampick.common.GlobalConst.PAGING_SIZE;

@Getter
@Setter
@ToString
public class GetInfoRequestDto extends Paging {
    private long glampId;

    public GetInfoRequestDto(Integer page) {
        super(page, PAGING_SIZE);
    }
}