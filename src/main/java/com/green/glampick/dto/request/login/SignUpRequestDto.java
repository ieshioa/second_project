package com.green.glampick.dto.request.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.glampick.security.SignInProviderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

    @JsonIgnore private long userId;

    @JsonIgnore private String providerId;
    @JsonIgnore private SignInProviderType userSocialType;

    @NotBlank @Email
    private String userEmail;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String userPw;

    @NotBlank @Pattern(regexp = "^[0-9]{11,13}$")
    private String userPhone;

    @NotBlank
    private String userName;

    @NotBlank @Pattern(regexp = "^[a-zA-Z가-힣][a-zA-Z0-9가-힣]{2,10}$")
    private String userNickname;

    private String userProfileImage;

}