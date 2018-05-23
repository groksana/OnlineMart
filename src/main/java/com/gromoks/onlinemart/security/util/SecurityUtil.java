package com.gromoks.onlinemart.security.util;

import com.gromoks.onlinemart.security.config.SecurityConfig;
import com.gromoks.onlinemart.security.entity.UserRole;
import com.gromoks.onlinemart.web.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public class SecurityUtil {
    public static boolean isSecurityPage(HttpServletRequest request) {
        String uri = request.getRequestURI();
        Set<UserRole> userRoles = SecurityConfig.getAllUserRole();

        for (UserRole userRole : userRoles) {
            List<String> uriPatterns = SecurityConfig.getUrlListForRole(userRole);
            if (uriPatterns != null && uriPatterns.contains(uri)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = RequestParser.getUrlPattern(request);
        Set<UserRole> userRoles = SecurityConfig.getAllUserRole();

        for (UserRole userRole : userRoles) {
            if (!request.isUserInRole(userRole.toString())) {
                continue;
            }
            List<String> urlPatterns = SecurityConfig.getUrlListForRole(userRole);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
}
