package com.green.glampick.repository.resultset;




public interface GetBookResultSet {

    String getCreatedAt();

    long getReservationId();

    String getCheckInDate();

    String getCheckOutDate();

    String getGlampName();

    String getRoomName();

    String getCheckInTime();

    String getCheckOutTime();

    long getReservationStatus();
}
