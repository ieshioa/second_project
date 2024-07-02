package com.green.glampick.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GlampingSearch {
    @Schema(example = "대구", description = "지역")
    private String region;
    @Schema(example = "2024-06-28", description = "체크인")
    private String inDate;
    @Schema(example = "2024-06-29", description = "체크아웃")
    private String outDate;
    @Schema(example = "2", description = "인원수")
    private int people;
    @Schema(example = "그린", description = "검색어")
    private String searchWord;

    @Schema(example = "2", description = "검색 결과 정렬 기준")
    private int sortType;
    @Schema(example = "[1, 2, 3]", description = "선택된 서비스")
    private List<Integer> filter;
}
