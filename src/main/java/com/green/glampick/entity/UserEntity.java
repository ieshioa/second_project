package com.green.glampick.entity;

import com.green.glampick.common.Role;
import com.green.glampick.dto.request.login.SignUpRequestDto;
import com.green.glampick.dto.request.user.GetBookRequestDto;
import com.green.glampick.dto.request.user.UpdateUserRequestDto;
import com.green.glampick.security.SignInProviderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;  // 유저 PK

    private String userEmail;  // 유저 이메일

    @Setter
    private String userPw;  // 유저 비밀번호

    private String userName;  // 유저 실명

    @Setter
    private String userNickname;  // 유저 닉네임

    @Setter
    private String userPhone;  // 유저 휴대폰 번호

    @Setter
    private String userProfileImage;  // 유저 프로필 이미지

    private String userRole;  // 유저 권한

    private String providerId;  // 소셜 유저 ID

    private String userSocialType;  // 소셜 로그인 타입

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserEntity(SignUpRequestDto dto) {
        this.userEmail = dto.getUserEmail();
        this.userPw = dto.getUserPw();
        this.userPhone = dto.getUserPhone();
        this.userName = dto.getUserName();
        this.userNickname = dto.getUserNickname();
        this.userRole = dto.getUserRole();
        this.userSocialType = dto.getUserSocialType();
    }

    public UserEntity(UpdateUserRequestDto dto) {
        this.userPw = dto.getUserPw();
        this.userNickname = dto.getUserNickname();
    }

    public UserEntity(long userId
            , String providerId
            , String userPw
            , String userName
            , String userProfileImage
            ) {
        this.userId = userId;
        this.providerId = providerId;
        this.userPw = userPw;
        this.userName = userName;
        this.userProfileImage= userProfileImage;
    }
}
