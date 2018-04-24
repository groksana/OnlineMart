package com.gromoks.onlinemart.web.filter;

import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.security.entity.UserRole;
import com.gromoks.onlinemart.security.util.SecurityUtil;
import com.gromoks.onlinemart.web.request.UserRoleRequestWrapper;

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

        HttpServletRequest wrapHttpServletRequest;
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

            boolean hasPermission = SecurityUtil.hasPermission(wrapHttpServletRequest);
            if (!hasPermission) {
                httpServletResponse.sendRedirect("/accessdenied");
                return;
            }
            filterChain.doFilter(wrapHttpServletRequest, httpServletResponse);
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
