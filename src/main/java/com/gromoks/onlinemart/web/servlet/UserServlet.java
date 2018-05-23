package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.gromoks.onlinemart.web.util.RequestParser.checkAddProductState;
import static com.gromoks.onlinemart.web.util.RequestParser.getSecurityToken;

public class UserServlet extends HttpServlet {
    private SessionStore sessionStore;

    public void setSessionStore(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = getSecurityToken(req, sessionStore);

        User user = sessionStore.getUserByToken(token);
        String addProductState = checkAddProductState(req);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("addProductState", addProductState);
        String page = thymeLeafPageGenerator.getHtmlPage("user", map);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setCharacterEncoding( "UTF-8" );
        PrintWriter writer = resp.getWriter();
        writer.write(page);
    }
}
