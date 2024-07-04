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
    private String id;  // 결제 ID
    private long reservationId;  // 예약 PK
    private long userId;  // 결제 유저 PK
    private String pg;  // 결제 수단
    private long payAmount;  // 결제 금액


    public PaymentEntity(PostPaymentRequestDto p) {
        this.id = p.getId();
        this.reservationId = p.getReservationId();
        this.pg = p.getPg();
        this.payAmount = p.getPayAmount();
    }
}
