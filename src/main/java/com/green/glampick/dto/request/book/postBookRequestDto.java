package com.green.glampick.dto.request.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class postBookRequestDto {

    @JsonIgnore private long reservationId;
    @JsonIgnore private String bookId;
    @JsonIgnore private long userId;
    private long glampId;
    private long roomId;
    private String inputName;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    private String pg;
    private long payAmount;


}
