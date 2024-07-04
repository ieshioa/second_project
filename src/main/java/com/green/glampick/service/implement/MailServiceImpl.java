package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
import com.green.glampick.repository.UserRepository;
import com.green.glampick.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, Integer> authCodeMap;
    private Map<String, Long> authCodeExpiryMap;

    //  최초 실행 시, 초기화를 한번만 진행  //
    @PostConstruct
    @Override
    public void init() {
        authCodeMap = new ConcurrentHashMap<>();
        authCodeExpiryMap = new ConcurrentHashMap<>();
    }

    //  6자리의 랜덤 숫자코드를 생성  //
    @Override
    public int createKey() {
        Random random = new Random();
        int key = 0;
        for (int i = 0; i < 6; i++) {
            key = key * 10 + random.nextInt(10);
        }
        return key;
    }

    //  이메일 인증 보내기  //
    @Override
    public ResponseEntity<? super PostMailSendResponseDto> sendAuthCode(String userEmail) {

        try {

            //  입력받은 이메일이 비어있는 값이면, 빈 값에 대한 응답을 보낸다.  //
            if (userEmail == null || userEmail.isEmpty()) {
                return PostMailSendResponseDto.nullEmptyEmail();
            }

            //  입력받은 이메일이 유저 테이블에 이미 있는 이메일 이라면, 중복 이메일에 대한 응답을 보낸다.  //
            boolean existedEmail = userRepository.existsByUserEmail(userEmail);
            if (existedEmail) { return PostMailSendResponseDto.duplicatedEmail(); }

            //  변수에 랜덤으로 생성되는 6자리의 숫자를 넣는다.  //
            int authCode = createKey();

            //  Map 객체에 유저 이메일과 위에서 생성한 코드를 추가하고, 유효시간은 5분으로 지정한다. (5분뒤 삭제) //
            authCodeMap.put(userEmail, authCode);
            authCodeExpiryMap.put(userEmail, System.currentTimeMillis() + 300000);

            //  MimeMessage 객체를 만든다.  //
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            //  MimeMessage 에 받아온 유저 이메일과, Text, Code 에 대한 값을 넣는다.  //
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(userEmail);
            helper.setSubject("Your Authentication Code");
            helper.setText("Your authentication code is: " + authCode, true);

            //  위에서 정의한 MimeMessage 를 전송한다.  //
            mailSender.send(mimeMessage);

            return PostMailSendResponseDto.success(authCode);

        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    //  이메일 코드 체크하기  //
    @Override
    public ResponseEntity<? super PostMailCheckResponseDto> checkCode(String userEmail, int authKey) {

        try {
            //  이메일과 인증코드가 Map 에 저장되어 있는 인증코드와 같다면  //
            if (authCodeMap.containsKey(userEmail) && authCodeMap.get(userEmail).equals(authKey)) {

                //  Map 에 저장되어 있는 인증코드의 유효시간이 지났다면  //
                if (System.currentTimeMillis() > authCodeExpiryMap.get(userEmail)) {

                    //  Map 에 저장되어 있는 정보를 삭제하고, 유효시간이 만료된 응답을 보낸다.  //
                    authCodeMap.remove(userEmail);
                    authCodeExpiryMap.remove(userEmail);
                    return PostMailCheckResponseDto.expiredCode();

                }

                //  인증 성공 시, Map 에 저장되어 있는 코드와 유효시간을 삭제한다.  //
                authCodeMap.remove(userEmail);
                authCodeExpiryMap.remove(userEmail);

                return PostMailCheckResponseDto.success();

            } else {

                //  인증코드가 틀리다면 틀린 인증번호에 대한 응답을 보낸다.  //
                return PostMailCheckResponseDto.invalidCode();

            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    //  1분 마다 스케줄이 실행되는 메소드  //
    @Scheduled(fixedRate = 60000)
    public void cleanUpExpiredCodes() {
        long now = System.currentTimeMillis();
        authCodeExpiryMap.entrySet().removeIf(entry -> now > entry.getValue());
    }
}