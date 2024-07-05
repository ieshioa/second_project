package com.green.glampick.service.implement;

import com.green.glampick.common.coolsms.SmsUtils;
import com.green.glampick.common.security.AppProperties;
import com.green.glampick.common.security.CookieUtils;
import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.login.SignInRequestDto;
import com.green.glampick.dto.request.login.SignUpRequestDto;
import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.dto.response.login.mail.PostMailCheckResponseDto;
import com.green.glampick.dto.response.login.sms.PostSmsCheckResponseDto;
import com.green.glampick.dto.response.login.sms.PostSmsSendResponseDto;
import com.green.glampick.dto.response.login.token.GetAccessTokenResponseDto;
import com.green.glampick.entity.UserEntity;
import com.green.glampick.jwt.JwtTokenProvider;
import com.green.glampick.repository.UserRepository;
import com.green.glampick.security.AuthenticationFacade;
import com.green.glampick.security.MyUser;
import com.green.glampick.security.MyUserDetail;
import com.green.glampick.service.LoginService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AppProperties appProperties;
    private final SmsUtils smsUtils;

    private Map<String, Integer> phoneCodeMap;
    private Map<String, Long> phoneCodeExpiryMap;

    //  최초 실행 시, 초기화를 한번만 진행  //
    @PostConstruct
    @Override
    public void init() {
        phoneCodeMap = new ConcurrentHashMap<>();
        phoneCodeExpiryMap = new ConcurrentHashMap<>();
    }

    //  이메일 회원가입 처리  //
    @Override
    public ResponseEntity<? super PostSignUpResponseDto> signUpUser(SignUpRequestDto dto) {

        try {

            //  입력받은 이메일이 유저 테이블에 이미 있는 이메일 이라면, 중복 이메일에 대한 응답을 보낸다.  //
            String userEmail = dto.getUserEmail();
            boolean existedEmail = userRepository.existsByUserEmail(userEmail);
            if (existedEmail) { return PostSignUpResponseDto.duplicatedEmail(); }

            //  입력받은 닉네임이 유저 테이블에 이미 있는 닉네임 이라면, 중복 닉네임에 대한 응답을 보낸다.  //
            String userNickname = dto.getUserNickname();
            boolean existedNickname = userRepository.existsByUserNickname(userNickname);
            if (existedNickname) { return PostSignUpResponseDto.duplicatedNickname(); }

            //  입력받은 전화번호가 유저 테이블에 이미 있는 전화번호 이라면, 중복 전화번호에 대한 응답을 보낸다.  //
            String userPhone = dto.getUserPhone();
            boolean existedPhone = userRepository.existsByUserPhone(userPhone);
            if (existedPhone) { return PostSignUpResponseDto.duplicatedPhoneNumber(); }

            //  입력받은 DTO 에서 패스워드를 암호화 하여 다시 DTO UserPw 값에 넣는다.  //
            String userPw = dto.getUserPw();
            String encodingPw = passwordEncoder.encode(userPw);
            dto.setUserPw(encodingPw);

            //  가공이 끝난 DTO 를 새로운 userEntity 객체로 생성한다.  //
            UserEntity userEntity = new UserEntity(dto);
            //  바로 위에서 만든 객체를 JPA 를 통해서 DB에 저장한다.  //
            UserEntity savedUser = userRepository.save(userEntity);

            return PostSignUpResponseDto.success(savedUser.getUserId());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

    }

    //  이메일 로그인 처리  //
    @Override
    public ResponseEntity<? super PostSignInResponseDto> signInUser(HttpServletResponse res, SignInRequestDto dto) {

        String accessToken = null;
        String refreshToken = null;

        try {

            //  입력받은 이메일이 유저 테이블에 없다면, 로그인 실패에 대한 응답을 보낸다.  //
            String userEmail = dto.getUserEmail();
            UserEntity userEntity = userRepository.findByUserEmail(userEmail);
            if (userEntity == null) { return PostSignInResponseDto.signInFailed(); }

            //  입력받은 비밀번호와 유저 테이블에 있는 비밀번호가 같은지 확인하고, 다르다면 로그인 실패에 대한 응답을 보낸다.  //
            String userPw = dto.getUserPw();
            String encodingPw = userEntity.getUserPw();
            boolean matches = passwordEncoder.matches(userPw, encodingPw);
            if (!matches) { return PostSignInResponseDto.signInFailed(); }

            //  로그인에 성공할 경우, myUser 에 로그인한 userId 값을 넣고, 권한을 넣는다.  //
            MyUser myUser = MyUser.builder()
                    .userId(userEntity.getUserId())
                    .role("ROLE_USER")
                    .build();

            //  myUser 에 넣은 데이터를 통해, AccessToken, RefreshToken 을 만든다.  //
            accessToken = jwtTokenProvider.generateAccessToken(myUser);
            refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

            //  RefreshToken 을 갱신한다.  //
            int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
            cookieUtils.deleteCookie(res, "refresh-token");
            cookieUtils.setCookie(res, "refresh-token", refreshToken, refreshTokenMaxAge);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostSignInResponseDto.success(accessToken);

    }

    //  AccessToken 불러오기  //
    @Override
    public ResponseEntity<? super GetAccessTokenResponseDto> getAccessToken(HttpServletRequest req) {

        //  req 에서 "refresh-token" 이름의 쿠키를 가져와서 cookie 에 저장하며, cookie 가 없다면 예외를 발생한다.  //
        Cookie cookie = cookieUtils.getCookie(req, "refresh-token");
        if(cookie == null) { throw new RuntimeException(); }

        //  RefreshToken 이 유효한지 확인하고, 유효하지 않다면 예외를 발생한다.  //
        String refreshToken = cookie.getValue();
        if(!jwtTokenProvider.isValidateToken(refreshToken)) { throw new RuntimeException(); }

        //  RefreshToken 에서 사용자의 세부 정보를 가져오고, 해당 정보를 통해서 MyUser 객체를 가져온다.  //
        UserDetails auth = jwtTokenProvider.getUserDetailsFromToken(refreshToken);
        MyUser myUser = ((MyUserDetail)auth).getMyUser();

        //  위에서 가져온 MyUser 정보가 담긴 AccessToken 을 가져온다.  //
        String accessToken = jwtTokenProvider.generateAccessToken(myUser);

        //  Map 객체에 AccessToken 을 추가한다. (키는 AccessToken, 값은 accessToken)  //
        Map map = new HashMap();
        map.put("accessToken", accessToken);

        return GetAccessTokenResponseDto.success(map);
    }

    //  휴대폰 인증 문자 보내기  //
    @Override
    public ResponseEntity<? super PostSmsSendResponseDto> sendOne(String userPhone) {

        int verificationCode;

        try {

            //  받아온 유저 휴대폰 번호의 "-" 부분을 없앤다. (010-1234-5678 -> 01012345678)  //
            userPhone.replaceAll("-","");

            //  변수에 랜덤으로 생성되는 6자리의 숫자를 넣는다.  //
            verificationCode = createKey();

            //  Map 객체에 유저 휴대폰 번호와 위에서 생성한 코드를 추가하고, 유효시간은 5분으로 지정한다. (5분뒤 삭제) //
            phoneCodeMap.put(userPhone, verificationCode);
            phoneCodeExpiryMap.put(userPhone, System.currentTimeMillis() + 300000);

            //  Cool SMS 를 통하여, 받아온 유저 휴대폰 번호에 코드를 보낸다.  //
            smsUtils.sendOne(userPhone, verificationCode);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostSmsSendResponseDto.success(verificationCode);

    }

    //  휴대폰 인증코드 체크하기  //
    @Override
    public ResponseEntity<? super PostSmsCheckResponseDto> checkPhone(String userPhone, int phoneKey) {

        try {
            //  이메일과 인증코드가 Map 에 저장되어 있는 인증코드와 같다면  //
            if (phoneCodeMap.containsKey(userPhone) && phoneCodeMap.get(userPhone).equals(phoneKey)) {

                //  Map 에 저장되어 있는 인증코드의 유효시간이 지났다면  //
                if (System.currentTimeMillis() > phoneCodeExpiryMap.get(userPhone)) {

                    //  Map 에 저장되어 있는 정보를 삭제하고, 유효시간이 만료된 응답을 보낸다.  //
                    phoneCodeMap.remove(userPhone);
                    phoneCodeExpiryMap.remove(userPhone);
                    return PostSmsCheckResponseDto.expiredCode();

                }

                //  인증 성공 시, Map 에 저장되어 있는 코드와 유효시간을 삭제한다.  //
                phoneCodeMap.remove(userPhone);
                phoneCodeExpiryMap.remove(userPhone);

                return PostSmsCheckResponseDto.success();

            } else {

                //  인증코드가 틀리다면 틀린 인증번호에 대한 응답을 보낸다.  //
                return PostSmsCheckResponseDto.invalidCode();

            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

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

    //  1분 마다 스케줄이 실행되는 메소드  //
    @Scheduled(fixedRate = 60000)
    public void cleanUpExpiredCodes() {
        long now = System.currentTimeMillis();
        phoneCodeExpiryMap.entrySet().removeIf(entry -> now > entry.getValue());
    }
}