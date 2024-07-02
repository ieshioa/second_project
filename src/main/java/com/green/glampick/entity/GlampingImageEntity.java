package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "glamping_image")
@Table(name = "glamping_image")
public class GlampingImageEntity {
//글램핑 이미지 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long glamp_image_id;//글램핑 이미지 ID
    private long glamp_id;//글램핑 ID
    private String glamp_image_name;//글램핑 이미지명
    private String created_at;//글램핑 이미지 등록 일자


}
