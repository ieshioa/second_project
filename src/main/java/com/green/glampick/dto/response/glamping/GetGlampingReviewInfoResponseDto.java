package com.green.glampick.dto.response.glamping;

import com.green.glampick.dto.object.glamping.GlampingReviewListItem;
import lombok.Data;

import java.util.List;

@Data
public class GetGlampingReviewInfoResponseDto {
    private double avgStarPoint;
    List<GlampingReviewListItem> reviewListItems;
}
