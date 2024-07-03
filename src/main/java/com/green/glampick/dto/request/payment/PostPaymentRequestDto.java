package com.green.glampick.dto.request.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPaymentRequestDto {

    private String id;
    private long reservationId;
    private String pg;
    private long payAmount;

}
