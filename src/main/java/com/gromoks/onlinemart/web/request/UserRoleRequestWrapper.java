package com.gromoks.onlinemart.web.request;

import com.gromoks.onlinemart.security.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

import static com.gromoks.onlinemart.security.entity.UserRole.getByName;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
    private String user;
    private UserRole userRole;
    private HttpServletRequest httpServletRequest;

    public UserRoleRequestWrapper(String user, UserRole userRole, HttpServletRequest request) {
        super(request);
        this.user = user;
        this.userRole = userRole;
        this.httpServletRequest = request;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (role == null) {
            return this.httpServletRequest.isUserInRole(role);
        }
        return getByName(role) == userRole;
    }

    @Override
    public Principal getUserPrincipal() {
        if (this.user == null) {
            return this.httpServletRequest.getUserPrincipal();
        }
        return () -> user;
    }
}
