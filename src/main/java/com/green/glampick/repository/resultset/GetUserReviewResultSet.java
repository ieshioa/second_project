package com.green.glampick.repository.resultset;

import com.green.glampick.entity.ReviewImageEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface GetUserReviewResultSet {

     String getGlampName();
     String getRoomName();
     String getUserNickname();
     String getUserProfileImage();
     long getReviewId();
     long getReservationId();
     String getReviewContent();
     int getReviewStarPoint();
     String getOwnerReviewComment();
     LocalDateTime getCreatedAt();
     long getBookId();
     long getGlampId();

}
