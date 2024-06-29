package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailSendResponseDto;
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

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> authCodeMap;
    private Map<String, Long> authCodeExpiryMap;

    //  인증 코드와 인증 코드의 만료시간을 저장할 맵이 초기화될 때, 실행되는 초기화 메소드  //
    @PostConstruct
    @Override
    public void init() {
        authCodeMap = new ConcurrentHashMap<>();
        authCodeExpiryMap = new ConcurrentHashMap<>();
    }

    //  6자리의 랜덤 인증코드를 생성하는 메소드  //
    @Override
    public String createKey() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            key.append(random.nextInt(10));
        }
        return key.toString();
    }

    //  인증 코드를 생성하여 HashMap 에 저장하고 이메일로 전송하는 메소드  //
    @Override
    public ResponseEntity<? super PostMailSendResponseDto> sendAuthCode(String userEmail) {

//        UserEntity userEntity = null;

        try {

            if (userEmail == null || userEmail.isEmpty()) {
                return PostMailSendResponseDto.nullEmptyEmail();
            }
//            if (userEntity == null) { return PostMailSendResponseDto.duplicatedEmail(); }

            String authCode = createKey();
            authCodeMap.put(userEmail, authCode);
            authCodeExpiryMap.put(userEmail, System.currentTimeMillis() + 300000); // 5분 후 만료

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(userEmail);
            helper.setSubject("Your Authentication Code");
            helper.setText("Your authentication code is: " + authCode, true);

            mailSender.send(mimeMessage);

            return PostMailSendResponseDto.success(authCode);

        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    //  인증 코드를 확인하고, 해당 인증코드가 만료되었는지 체크하는 메소드  //
    @Override
    public ResponseEntity<? super PostMailCheckResponseDto> checkCode(String userEmail, String authKey) {

        try {
            // 이메일과 인증코드가 Map 에 저장되어 있는 인증코드와 같다면
            if (authCodeMap.containsKey(userEmail) && authCodeMap.get(userEmail).equals(authKey)) {
                // Map 에 저장되어 있는 인증코드의 유효시간이 지났다면
                if (System.currentTimeMillis() > authCodeExpiryMap.get(userEmail)) {
                    authCodeMap.remove(userEmail);
                    authCodeExpiryMap.remove(userEmail);
                    PostMailCheckResponseDto.expiredCode();
                }
                // 인증 성공 시, Map 에 저장되어 있는 코드와 유효시간을 삭제한다.
                authCodeMap.remove(userEmail);
                authCodeExpiryMap.remove(userEmail);
                return PostMailCheckResponseDto.success();
            } else {
                // 인증코드가 틀리다면 아래 코드를 실행한다.
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