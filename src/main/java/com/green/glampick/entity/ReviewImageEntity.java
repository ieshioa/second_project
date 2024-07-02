package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "review_image")
@Table(name = "review_image")
public class ReviewImageEntity {
    //객실 이미지 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long room_image_id;//객실 이미지 ID
    private long room_id;//객실 ID
    private String room_image_name;//객실 이미지명
    private String created_at;//객실 이미지 등록 일자


}
