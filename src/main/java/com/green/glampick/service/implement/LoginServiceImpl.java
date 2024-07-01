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
    private final AuthenticationFacade authenticationFacade;
    private final AppProperties appProperties;
    private final SmsUtils smsUtils;

    private Map<String, String> phoneCodeMap;
    private Map<String, Long> phoneCodeExpiryMap;

    @PostConstruct
    @Override
    public void init() {
        phoneCodeMap = new ConcurrentHashMap<>();
        phoneCodeExpiryMap = new ConcurrentHashMap<>();
    }

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

            MyUser myUser = MyUser.builder()
                    .userId(userEntity.getUserId())
                    .role("ROLE_USER")
                    .build();

            accessToken = jwtTokenProvider.generateAccessToken(myUser);
            refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

            int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
            cookieUtils.deleteCookie(res, "refresh-token");
            cookieUtils.setCookie(res, "refresh-token", refreshToken, refreshTokenMaxAge);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostSignInResponseDto.success(accessToken);

    }

    @Override
    public ResponseEntity<? super GetAccessTokenResponseDto> getAccessToken(HttpServletRequest req) {

        Cookie cookie = cookieUtils.getCookie(req, "refresh-token");
        if(cookie == null) { throw new RuntimeException(); }

        String refreshToken = cookie.getValue();
        if(!jwtTokenProvider.isValidateToken(refreshToken)) { throw new RuntimeException(); }

        UserDetails auth = jwtTokenProvider.getUserDetailsFromToken(refreshToken);
        MyUser myUser = ((MyUserDetail)auth).getMyUser();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);

        Map map = new HashMap();
        map.put("accessToken", accessToken);
        return GetAccessTokenResponseDto.success(map);
    }

    @Override
    public ResponseEntity<? super PostSmsSendResponseDto> sendOne(String userPhone) {

        String verificationCode = null;

        try {

            userPhone.replaceAll("-","");
            verificationCode = createKey();
            phoneCodeMap.put(userPhone, verificationCode);
            phoneCodeExpiryMap.put(userPhone, System.currentTimeMillis() + 300000); // 5분 후 만료

            smsUtils.sendOne(userPhone, verificationCode);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostSmsSendResponseDto.success(verificationCode);

    }

    @Override
    public ResponseEntity<? super PostSmsCheckResponseDto> checkPhone(String userPhone, String phoneKey) {

        try {
            // 이메일과 인증코드가 Map 에 저장되어 있는 인증코드와 같다면
            if (phoneCodeMap.containsKey(userPhone) && phoneCodeMap.get(userPhone).equals(phoneKey)) {
                // Map 에 저장되어 있는 인증코드의 유효시간이 지났다면
                if (System.currentTimeMillis() > phoneCodeExpiryMap.get(userPhone)) {
                    phoneCodeMap.remove(userPhone);
                    phoneCodeExpiryMap.remove(userPhone);
                    return PostSmsCheckResponseDto.expiredCode();
                }
                // 인증 성공 시, Map 에 저장되어 있는 코드와 유효시간을 삭제한다.
                phoneCodeMap.remove(userPhone);
                phoneCodeExpiryMap.remove(userPhone);
                return PostSmsCheckResponseDto.success();
            } else {
                // 인증코드가 틀리다면 아래 코드를 실행한다.
                return PostSmsCheckResponseDto.invalidCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

    }

    @Override
    public String createKey() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            key.append(random.nextInt(10));
        }
        return key.toString();
    }

}