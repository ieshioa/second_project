package com.green.glampick.entity;

import com.green.glampick.dto.request.login.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private long userId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private String userPhone;

    @Column(nullable = true)
    private String userProfileImage;

    @Column(nullable = false)
    private String userRole;

    @Column(nullable = true)
    private String providerId;

    @Column(nullable = true)
    private String userSocialType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserEntity(SignUpRequestDto dto) {
        this.userEmail = dto.getUserEmail();
        this.userPw = dto.getUserPw();
        this.userPhone = dto.getUserPhone();
        this.userName = dto.getUserName();
        this.userNickname = dto.getUserNickname();
        this.userProfileImage = dto.getUserProfileImage();
        this.userRole = "USER";
    }
}
