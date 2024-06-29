package com.green.glampick.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        Dotenv dotenv = Dotenv.load();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(dotenv.get("MAIL_HOST")); // SMTP Server Host
        mailSender.setPort(Integer.parseInt(dotenv.get("MAIL_PORT"))); // SMTP Server Port
        mailSender.setUsername(dotenv.get("MAIL_USERNAME")); // User Email
        mailSender.setPassword(dotenv.get("MAIL_PASSWORD")); // User App Key

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", "smtp"); // SMTP 프로토콜 사용
        javaMailProperties.put("mail.smtp.auth", "true");// SMTP 서버에 인증을 필요
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL 소켓 팩토리 클래스 사용
        javaMailProperties.put("mail.smtp.starttls.enable", "true"); // STARTTLS(TLS를 시작하는 명령)를 사용하여 암호화된 통신을 활성화
        javaMailProperties.put("mail.debug", "true"); // 디버깅 정보 출력
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.google.com"); // smtp 서버의 ssl 인증서를 신뢰
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 사용할 ssl 프로토콜 버젼

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

}
