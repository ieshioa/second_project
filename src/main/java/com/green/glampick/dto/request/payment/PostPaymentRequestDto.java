package com.green.glampick.dto.request.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPaymentRequestDto {

    private String id;
    private long reservationId;
    @JsonIgnore private long userId;
    private String pg;
    private long payAmount;

}
