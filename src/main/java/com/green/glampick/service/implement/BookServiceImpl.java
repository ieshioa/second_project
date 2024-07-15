package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.book.postBookRequestDto;
import com.green.glampick.dto.response.book.PostBookResponseDto;
import com.green.glampick.dto.response.owner.post.PostGlampingInfoResponseDto;
import com.green.glampick.entity.ReservationBeforeEntity;
import com.green.glampick.repository.ReservationBeforeRepository;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final ReservationBeforeRepository reservationBeforeRepository;
    private final AuthenticationFacade authenticationFacade;

    //  글램핑 예약하기  //
    @Override
    public ResponseEntity<? super PostBookResponseDto> postBook(postBookRequestDto dto) {

        //  로그인 유저가 없다면, 권한이 없다는 응답을 보낸다.  //
        try {
            dto.setUserId(authenticationFacade.getLoginUserId());
            if (dto.getUserId() <= 0) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return PostBookResponseDto.validateUserId();
        }

        ReservationBeforeEntity reservationBeforeEntity = new ReservationBeforeEntity(dto);

        try {
            //  중복된 예약 내역이 있는지 확인하고 있다면 중복된 예약내역에 대한 응답을 보낸다.  //
            boolean existedReservation = reservationBeforeRepository.existsByReservationId
                                                                    (reservationBeforeEntity.getReservationId());
            if (existedReservation) { return PostBookResponseDto.duplicatedBook(); }

            if (checkDate(dto.getCheckInDate(), dto.getCheckOutDate())) { return PostBookResponseDto.wrongDate(); }

            //  현재시간을 기준으로 랜덤 13자리의 수를 생성한다.  //
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");  // 날짜 포맷 설정 (년월일시분초)
            String dateString = dateFormat.format(new Date(currentTimeMillis));
            Random random = new Random();
            int randomNum = random.nextInt(10);  // 0-9까지의 숫자 중 하나 생성
            String reservationNumber = dateString + randomNum;  // 예약번호 생성 (날짜 + 난수)

            //  생성한 랜덤 13자리 수를 예약번호에 넣는다.  //
            reservationBeforeEntity.setBookId(reservationNumber);

            //  가공이 완료된 Entity 를 DB에 저장한다.  //
            reservationBeforeRepository.save(reservationBeforeEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostBookResponseDto.success();

    }

    private boolean checkDate (String in, String out) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inDate = LocalDate.parse(in, formatter);
        LocalDate outDate = LocalDate.parse(out, formatter);
        return outDate.isBefore(inDate); // 틀리면 true
    }

}
