package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "glamp_favorite")
@Table(name = "glamp_favorite")
public class GlampFavoriteEntity {
    //관심 글램핑 테이블
    @Id
    private long userId;//유저 ID
    private long glampId;//글램핑 ID
    private String createdAt;//관심 설정 일자

}
