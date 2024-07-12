package com.green.glampick.repository.resultset;

import com.green.glampick.entity.ReviewImageEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface GetUserReviewResultSet {

     long getReviewId();
     String getGlampName();
     String getRoomName();
     String getUserNickname();
     String getUserProfileImage();
     String getReviewContent();
     int getReviewStarPoint();
     String getOwnerReviewComment();
     LocalDateTime getCreatedAt();
     String getStarPointAvg();

}
