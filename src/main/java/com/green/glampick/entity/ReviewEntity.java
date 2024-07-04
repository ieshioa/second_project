package com.green.glampick.entity;

import com.green.glampick.dto.request.user.PostReviewRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "review")
@Table(name = "review")
public class ReviewEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;  // 리뷰 PK
    private long userId;  // 유저 PK
    private long reservationId;  // 예약 PK
    private String reviewContent;  // 리뷰내용
    private long reviewStarPoint;  // 리뷰 별점

    @CreationTimestamp
    @Column(updatable = false)
    private String createdAt;  // 리뷰작성일자


    public ReviewEntity (PostReviewRequestDto dto) {

        this.userId = dto.getUserId();
        this.reservationId = dto.getReservationId();
        this.reviewContent = dto.getReviewContent();
        this.reviewStarPoint = dto.getReviewStarPoint();
    }



}
