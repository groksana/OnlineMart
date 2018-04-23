package com.gromoks.onlinemart.security.config;

import com.gromoks.onlinemart.security.entity.UserRole;

import java.util.*;

public class SecurityConfig {
    private static final Map<UserRole, List<String>> MAP_CONFIG = new HashMap<>();

    static {init();}

    public static Set<UserRole> getAllUserRole() {
        return MAP_CONFIG.keySet();
    }

    public static List<String> getUrlListForRole(UserRole userRole) {
        return MAP_CONFIG.get(userRole);
    }

    private static void init() {
        //Configuration for Admin
        List<String> uriAdminAccess = new ArrayList<>();
        uriAdminAccess.add("/newproduct");
        uriAdminAccess.add("/cart");
        uriAdminAccess.add("/user");
        MAP_CONFIG.put(UserRole.ADMIN, uriAdminAccess);

        //Configuration for User
        List<String> uriUserAccess = new ArrayList<>();
        uriUserAccess.add("/cart");
        uriUserAccess.add("/user");
        MAP_CONFIG.put(UserRole.USER, uriUserAccess);
    }

}
