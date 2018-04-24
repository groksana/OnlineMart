package com.gromoks.onlinemart.web.filter;

import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.security.entity.UserRole;
import com.gromoks.onlinemart.web.request.UserRoleRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserRoleFilter implements Filter {
    private SessionStore sessionStore;

    public UserRoleFilter(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpServletRequest wrapHttpServletRequest;
        if (httpServletRequest.getUserPrincipal() == null) {
            Cookie[] cookies = httpServletRequest.getCookies();

            boolean isLoggedIn = false;
            User user = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String cookieName = cookie.getName();
                    if ("security-token".equals(cookieName)) {
                        if (sessionStore.isValid(cookie.getValue())) {
                            isLoggedIn = true;
                            user = sessionStore.getUserByToken(cookie.getValue());
                        }
                    }
                }
            }

            if (isLoggedIn) {
                String userName = user.getLogin();
                UserRole userRole = user.getRole();
                wrapHttpServletRequest = new UserRoleRequestWrapper(userName, userRole, httpServletRequest);
            } else {
                wrapHttpServletRequest = new UserRoleRequestWrapper(null, UserRole.GUEST, httpServletRequest);
            }
        } else {
            wrapHttpServletRequest = httpServletRequest;
        }
        chain.doFilter(wrapHttpServletRequest, httpServletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
