package com.green.glampick.config;

import com.green.glampick.jwt.JwtAuthenticationAccessDeniedHandler;
import com.green.glampick.jwt.JwtAuthenticationEntryPoint;
import com.green.glampick.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable())
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(
                                        //회원가입, 로그인 인증이 안 되어 있더라도 사용 가능하게 세팅
                                        "/api/auth/sign-up"
                                        , "/api/auth/sign-in"
                                        , "/api/auth/access-token"
                                        , "/api/auth/mail-send"
                                        , "/api/auth/mail-check"
                                        , "/api/auth/send-sms"
                                        , "/api/auth/check-sms"

                                        //swagger 사용할 수 있게 세팅
                                        , "/swagger"
                                        , "/swagger-ui/**"
                                        , "/v3/api-docs/**"

                                        //프론트 화면 보일수 있게 세팅
                                        ,"/"
                                        ,"/index.html"
                                        , "/css/**"
                                        , "/js/**"
                                        , "/static/**"

                                        //프론트에서 사용하는 라우터 주소
                                        , "/sign-in"
                                        , "/sign-up"
                                        , "/profile/*"
                                        , "/feed"

                                        //actuator
                                        , "/actuator"
                                        , "/actuator/*"

                                ).permitAll()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                        .accessDeniedHandler(new JwtAuthenticationAccessDeniedHandler())
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
