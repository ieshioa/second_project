package com.green.glampick.entity;

import com.green.glampick.dto.request.user.PostReviewRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "review")
@Table(name = "review")
public class ReviewEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;
    private long userId;
    private long reservationId;
    private String reviewContent;
    private long reviewStarPoint;

    @CreationTimestamp
    @Column(updatable = false)
    private String createdAt;


    public ReviewEntity (PostReviewRequestDto dto) {
        this.userId = dto.getUserId();
        this.reservationId = dto.getReservationId();
        this.reviewContent = dto.getReviewContent();
        this.reviewStarPoint = dto.getReviewStarPoint();
    }

}
