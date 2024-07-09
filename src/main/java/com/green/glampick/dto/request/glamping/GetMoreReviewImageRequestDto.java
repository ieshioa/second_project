package com.green.glampick.dto.request.glamping;

import com.green.glampick.common.GlobalConst;
import com.green.glampick.common.Paging;
import com.green.glampick.common.response.ResponseCode;
import com.green.glampick.common.response.ResponseMessage;
import com.green.glampick.dto.object.ReviewListItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@ToString
public class GetMoreReviewImageRequestDto extends Paging {
    private long glampId;

    public GetMoreReviewImageRequestDto(Integer page) {
        super(page, GlobalConst.REVIEW_IMAGE_SIZE);
    }

}
