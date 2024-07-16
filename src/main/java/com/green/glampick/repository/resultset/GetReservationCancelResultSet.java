package com.green.glampick.repository.resultset;

public interface GetReservationCancelResultSet {

    String getGlampName();
    String getGlampImage();
    String bookId();
    String getRoomName();
    String getCheckInDate();
    String getCheckOutDate();
    String getComment();
    String getCreatedAt();
    String getCheckInTime();
    String getCheckOutTime();

}
