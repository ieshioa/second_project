package com.green.glampick.repository;

import com.green.glampick.dto.response.payment.GetPaymentResponseDto;
import com.green.glampick.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

    PaymentEntity findBy(long reservationId);
}
