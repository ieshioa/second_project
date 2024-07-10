package com.green.glampick.repository.resultset;

import java.util.ArrayList;
import java.util.List;

public interface GetUserReviewResultSet {

     int getReviewStarPoint(); // 별점
     String getCreatedAt(); // 리뷰 작성 날짜
     String getUserProfileImage(); // 유저 사진
     String getUserNickname(); // 유저 닉네임
     String getGlampName();// 글램핑 이름
     String getRoomName();// 글램핑 호수
     String getReviewContent(); // 리뷰
     String getOwnerReviewComment(); // 숙소 답변

}
