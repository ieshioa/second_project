package com.green.glampick.entity;

import com.green.glampick.dto.request.user.PostReviewRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "review_image")
@Table(name = "review_image")
public class ReviewImageEntity {
    //객실 이미지 테이블
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewImageId;  //객실 이미지 ID
    private long reviewId;  //객실 ID
    private String reviewImageName;  //객실 이미지명
    @CreationTimestamp
    @Column(updatable = false)
    private String createdAt;  //객실 이미지 등록 일자

}
