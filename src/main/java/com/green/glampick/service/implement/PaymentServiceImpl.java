package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.payment.PostPaymentRequestDto;
import com.green.glampick.dto.response.payment.PostPaymentResponseDto;
import com.green.glampick.entity.PaymentEntity;
import com.green.glampick.repository.PaymentRepository;
import com.green.glampick.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public ResponseEntity<? super PostPaymentResponseDto> postPayment(PostPaymentRequestDto p) {

        String paytmentId = p.getId();

        try {
            PaymentEntity payment = new PaymentEntity(p);
            paymentRepository.save(payment);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostPaymentResponseDto.success(paytmentId);
    }

    @Override
    public ResponseEntity<? super GetPaymentResponseDto> getPayment(long reservationId) {

        PaymentEntity paymentEntities = null;

        try {

            paymentEntities = paymentRepository.findBy(reservationId);



        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.databaseError();
        }

        GetPaymentResponseDto.success(paymentEntities);

    }

}
