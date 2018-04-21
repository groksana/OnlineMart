package com.gromoks.onlinemart.web.util;

import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.security.entity.UserRole;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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

    public static String checkAddProductState(HttpServletRequest httpServletRequest, SessionStore sessionStore) {
        String token = getSecurityToken(httpServletRequest, sessionStore);
        Optional<User> user;
        if (token != null) {
            user = Optional.of(sessionStore.getUserByToken(token));
        } else {
            user = Optional.empty();
        }

        UserRole userRole = UserRole.GUEST;
        if (user.isPresent()) {
            userRole = user.get().getRole();
        }

        String addProductState;

        if (userRole == UserRole.ADMIN) {
            addProductState = "active";
        } else {
            addProductState = "disabled";
        }
        return addProductState;
    }
}
