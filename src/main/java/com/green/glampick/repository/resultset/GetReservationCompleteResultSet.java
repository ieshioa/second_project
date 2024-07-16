package com.green.glampick.repository.resultset;

public interface GetReservationCompleteResultSet {

    String getGlampName();
    String getGlampImage();
    String bookId();
    String getRoomName();
    String getCheckInDate();
    String getCheckOutDate();
    String getCreatedAt();
    String getCheckInTime();
    String getCheckOutTime();

}
