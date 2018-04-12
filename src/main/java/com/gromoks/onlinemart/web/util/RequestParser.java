package com.gromoks.onlinemart.web.util;

import com.gromoks.onlinemart.security.SessionStore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class RequestParser {
    public static String getSecurityToken(HttpServletRequest httpServletRequest, SessionStore sessionStore) {
        Cookie[] cookies = httpServletRequest.getCookies();

        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if ("security-token".equals(cookieName)) {
                    if (sessionStore.isValid(cookie.getValue())) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        return token;
    }
}
