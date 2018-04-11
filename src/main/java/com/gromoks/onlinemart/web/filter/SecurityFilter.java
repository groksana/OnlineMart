package com.gromoks.onlinemart.web.filter;

import com.gromoks.onlinemart.security.SessionStore;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {
    private SessionStore sessionStore;

    public SecurityFilter(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;

        if (httpServletRequest.getRequestURI().contains("/login")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        Cookie[] cookies = httpServletRequest.getCookies();

        boolean isLoggedIn = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if ("security-token".equals(cookieName)) {
                   if (sessionStore.isValid(cookie.getValue())) {
                       isLoggedIn = true;
                   }
                }
            }
        }

        if (isLoggedIn) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendRedirect("/login");
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
