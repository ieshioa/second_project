package com.green.glampick.repository;

import com.green.glampick.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

    PaymentEntity findByReservationId(long reservationId);
}
