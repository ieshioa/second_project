package com.green.glampick.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtils {

    private final ObjectMapper om;

    public <T> T getData(T type, HttpServletRequest req, String name) {

        try {

            Cookie cookie = getCookie(req, name);
            String json = cookie.getValue();
            return (T) om.readValue(json, type.getClass());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

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

    public void setCookie(HttpServletResponse res, String name, String value, int maxAge) {

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        res.addCookie(cookie);

    }

    public void deleteCookie(HttpServletResponse res, String name) {

        setCookie(res, name, null, 0);

    }

}
