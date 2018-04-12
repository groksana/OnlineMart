package com.gromoks.onlinemart.web.filter;

import com.gromoks.onlinemart.security.SessionStore;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import static com.gromoks.onlinemart.web.util.RequestParser.getSecurityToken;

public class MdcFilter implements Filter {

    private static final String GUEST = "guest";
    private SessionStore sessionStore;

    public MdcFilter(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String requestId = UUID.randomUUID().toString();
        String token = getSecurityToken(httpServletRequest, sessionStore);
        String email;

        if (token != null) {
            email = sessionStore.getUserByToken(token).getLogin();
        } else {
            email = GUEST;
        }

        MDC.put("requestId", requestId);
        MDC.put("email", email);

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
