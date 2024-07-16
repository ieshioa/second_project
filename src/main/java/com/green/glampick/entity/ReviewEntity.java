package com.green.glampick.entity;

import com.green.glampick.dto.request.user.PostReviewRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "review")
@Table(name = "review")
public class ReviewEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;  // 리뷰 PK
    private long userId;  // 유저 PK
    private long glampId;
    private long roomId;
    private String reviewContent;  // 리뷰내용
    private int reviewStarPoint;  // 리뷰 별점
    private String reviewComment;
    private long reservationId;
    @CreationTimestamp
    @Column(updatable = false)
    private String createdAt;  // 리뷰작성일자


    public ReviewEntity (PostReviewRequestDto dto) {

        this.userId = dto.getUserId();
        this.reviewContent = dto.getReviewContent();
        this.reviewStarPoint = dto.getReviewStarPoint();
        this.reservationId = dto.getReservationId();
    }



}
