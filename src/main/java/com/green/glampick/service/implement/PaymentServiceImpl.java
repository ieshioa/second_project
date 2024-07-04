package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.payment.GetPaymentRequestDto;
import com.green.glampick.dto.request.payment.PostPaymentRequestDto;
import com.green.glampick.dto.response.payment.PostPaymentResponseDto;
import com.green.glampick.entity.PaymentEntity;
import com.green.glampick.repository.PaymentRepository;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.security.MyUserDetail;
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
    private final AuthenticationFacade authenticationFacade;

    @Override
    public ResponseEntity<? super PostPaymentResponseDto> postPayment(PostPaymentRequestDto dto) {
        dto.setUserId(authenticationFacade.getLoginUserId());

        String paytmentId = dto.getId();

        try {
            PaymentEntity payment = new PaymentEntity(dto);
            paymentRepository.save(payment);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostPaymentResponseDto.success(paytmentId);
    }

    @Override
    public ResponseEntity<? super GetPaymentResponseDto> getPayment(GetPaymentRequestDto dto) {

        dto.setUserId(authenticationFacade.getLoginUserId());

        try {

            // reservationId 에 대한 모든 정보를 Entity 에 담아 온다.
            PaymentEntity paymentEntity = paymentRepository.findByReservationId(dto.getReservationId());

            // 예약내역이 존재하지 않는다면 존재하지 않는 예약내역에 대한 값을 반환한다.
            if (paymentEntity == null) { return GetPaymentResponseDto.noExistedPayment(); }

            // 로그인한 사용자가 다른 사용자의 예약내역을 불러오려한다면 권한이 없다는 값을 반환한다.
            if (paymentEntity.getUserId() != dto.getUserId()) { return GetPaymentResponseDto.noPermission();}


            return GetPaymentResponseDto.success(paymentEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

    }

}
