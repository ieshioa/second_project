package com.green.glampick.dto.request.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class postBookRequestDto {

    @JsonIgnore private long reservationId;
    @JsonIgnore private String bookId;
    @JsonIgnore private long userId;
    private long glampId;
    private long roomId;
    private String inputName;
    private String checkInDate;
    private String checkOutDate;
    private String pg;
    private long payAmount;


}
