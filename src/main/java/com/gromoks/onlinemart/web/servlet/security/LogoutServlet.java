package com.gromoks.onlinemart.web.servlet.security;

import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.gromoks.onlinemart.web.entity.TemplateMode.HTML;
import static com.gromoks.onlinemart.web.util.RequestParser.checkAddProductState;

public class LogoutServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private SessionStore sessionStore;

    public LogoutServlet(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start to logout");
        PrintWriter writer = resp.getWriter();
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if ("security-token".equals(cookieName)) {
                    sessionStore.removeSessionByToken(cookie.getValue());
                }
            }
        }

        Cookie cookie = new Cookie("security-token", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        String addProductState = checkAddProductState(req, sessionStore);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        Map<String, Object> map = new HashMap<>();
        map.put("addProductState", addProductState);
        String page = thymeLeafPageGenerator.getPage("logout", HTML, map);
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
        log.info("Finish to logout");
    }
}
