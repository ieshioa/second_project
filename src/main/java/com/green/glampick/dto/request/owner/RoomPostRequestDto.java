package com.green.glampick.dto.request.owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RoomPostRequestDto {

    @JsonIgnore
    private long roomId;

    @Schema(example = "1", description = "글램핑 PK")
    private long glampId;
    @Schema(example = "카라반 102호", description = "객실 이름")
    private String roomName;
    @Schema(example = "65500", description = "객실 가격")
    private int price;
    @Schema(example = "2", description = "기준 인원")
    private int peopleNum;
    @Schema(example = "6", description = "최대 인원")
    private int peopleMax;
    @Schema(example = "15:00:00", description = "체크인 시간")
    private String inTime;
    @Schema(example = "12:00:00", description = "체크아웃 시간")
    private String outTime;

    // 이미지
    @JsonIgnore
    private List<String> roomImgName;

    // 서비스
    private List<Integer> serviceList;




}
