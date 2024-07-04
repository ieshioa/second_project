package com.green.glampick.dto.request.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPaymentRequestDto {

    @JsonIgnore private long userId;
    private long reservationId;

}
