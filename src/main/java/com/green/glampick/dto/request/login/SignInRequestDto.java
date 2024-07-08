package com.green.glampick.dto.request.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequestDto {

    @NotBlank
    private String userEmail;

    @NotBlank
    private String userPw;

    @JsonIgnore
    private String providerId;

    @JsonIgnore
    private String userSocialType;


}