package com.green.glampick.dto.request.glamping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.glampick.common.GlobalConst;
import com.green.glampick.common.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static com.green.glampick.common.GlobalConst.PAGING_SIZE;

@Getter
@Setter
@ToString
public class GetInfoRequestDto {
    private long glampId;
    @Schema(example = "2024-06-10")
    private String inDate;
    @Schema(example = "2024-06-15")
    private String outDate;
    @Schema(example = "0")
    private int status;

    @JsonIgnore
    private int size;
    @JsonIgnore
    private long roomId;

    public GetInfoRequestDto(int status) {
        if (status == 0) {
            size = PAGING_SIZE;
        } else if(status == 1) {
            size = 0;
        }
    }

}