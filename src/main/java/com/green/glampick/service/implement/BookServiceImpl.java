package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.book.postBookRequestDto;
import com.green.glampick.dto.response.book.postBookResponseDto;
import com.green.glampick.entity.ReservationBeforeEntity;
import com.green.glampick.repository.ReservationBeforeRepository;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final ReservationBeforeRepository reservationBeforeRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public ResponseEntity<? super postBookResponseDto> postBook(postBookRequestDto dto) {
        long loggedInUserId = authenticationFacade.getLoginUserId();
        if (loggedInUserId == 0) { return postBookResponseDto.noPermission(); }
        dto.setUserId(authenticationFacade.getLoginUserId());

        ReservationBeforeEntity reservationBeforeEntity = new ReservationBeforeEntity(dto);

        try {
            boolean existedReservation = reservationBeforeRepository.existsByReservationId
                                                                    (reservationBeforeEntity.getReservationId());
            if (existedReservation) { return postBookResponseDto.duplicatedBook(); }


            long currentTimeMillis = System.currentTimeMillis();

            // 날짜 포맷 설정 (년월일시분초)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String dateString = sdf.format(new Date(currentTimeMillis));
            // 난수 생성기
            Random random = new Random();
            int randomNum = random.nextInt(10);  // 0-9까지의 숫자 중 하나 생성
            // 예약번호 생성 (날짜 + 난수)
            String reservationNumber = dateString + randomNum;

            reservationBeforeEntity.setBookId(reservationNumber);

            reservationBeforeRepository.save(reservationBeforeEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return postBookResponseDto.success();

    }

}
