package com.green.glampick.service.implement;

import com.green.glampick.dto.ResponseDto;
import com.green.glampick.dto.request.login.SignInRequestDto;
import com.green.glampick.dto.request.login.SignUpRequestDto;
import com.green.glampick.dto.response.login.PostSignInResponseDto;
import com.green.glampick.dto.response.login.PostSignUpResponseDto;
import com.green.glampick.entity.UserEntity;
import com.green.glampick.repository.UserRepository;
import com.green.glampick.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
//            String userPw = dto.getUserPw();
//            String encodingPw = passwordEncoder.encode(userPw);
//            dto.setUserPw(encodingPw);

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
    public ResponseEntity<? super PostSignInResponseDto> signInUser(SignInRequestDto dto) {

        try {

            //  입력받은 이메일이 유저 테이블에 없다면, 로그인 실패에 대한 응답을 보낸다.  //
            String userEmail = dto.getUserEmail();
            UserEntity userEntity = userRepository.findByUserEmail(userEmail);
            if (userEntity == null) { return PostSignInResponseDto.signInFailed(); }

            //  입력받은 비밀번호와 유저 테이블에 있는 비밀번호가 같은지 확인하고, 다르다면 로그인 실패에 대한 응답을 보낸다.  //
            String userPw = dto.getUserPw();
            String encodingPw = userEntity.getUserPw();
//            boolean matches = passwordEncoder.matches(userPw, encodingPw);
//            if (!matches) { return PostSignInResponseDto.signInFailed(); }

            if (!Objects.equals(userPw, encodingPw)) { return PostSignInResponseDto.signInFailed(); }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostSignInResponseDto.success();

    }

}