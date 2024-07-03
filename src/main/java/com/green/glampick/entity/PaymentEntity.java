package com.green.glampick.entity;

import com.green.glampick.dto.request.payment.PostPaymentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payment")
@Table(name = "payment")
public class PaymentEntity {

    @Id
    private String id;
    private long reservationId;
    private String pg;
    private long payAmount;


    public PaymentEntity(PostPaymentRequestDto p) {
        this.id = p.getId();
        this.reservationId = p.getReservationId();
        this.pg = p.getPg();
        this.payAmount = p.getPayAmount();
    }
}
