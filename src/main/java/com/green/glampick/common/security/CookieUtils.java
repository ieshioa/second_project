package com.green.glampick.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtils {

    //요청 header에 내가 원하는 쿠키를 찾는 메소드
    public Cookie getCookie(HttpServletRequest req, String name) {

        Cookie[] cookies = req.getCookies();
        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies) {

                if(name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;

    }

    public <T> T getCookie (HttpServletRequest req, String name, Class<T> valueType) {
        Cookie cookie = getCookie(req, name);
        if(cookie == null) { return null; }
        if(valueType == String.class) {
            return (T) cookie.getValue();
        }
        return deserialize(cookie, valueType);
    }

    public void setCookie(HttpServletResponse res, String name, String value, int maxAge) {

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        res.addCookie(cookie);

    }

    public void setCookie(HttpServletResponse res, String name, Object obj, int maxAge) {
        this.setCookie(res, name, serialize(obj), maxAge);
    }

    public void deleteCookie(HttpServletResponse res, String name) {

        setCookie(res, name, null, 0);

    }

    public String serialize(Object obj) { //객체가 가지고 있는 데이터를 문자열로 변환(암호화)
        // Object > byte[] > String
        return Base64.getUrlEncoder().encodeToString( SerializationUtils.serialize(obj) );
    }

    public <T> T deserialize(Cookie cookie, Class<T> cls) { // 복호화
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue()) //String > byte[] > Object
                )
        );
    }

}
