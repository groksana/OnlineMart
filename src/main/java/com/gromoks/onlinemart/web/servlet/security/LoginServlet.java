package com.gromoks.onlinemart.web.servlet.security;

import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.service.security.SessionStore;
import com.gromoks.onlinemart.service.security.entity.Session;
import com.gromoks.onlinemart.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

public class LoginServlet extends HttpServlet {

    private List<User> userList = new ArrayList<>();
    private SessionStore sessionStore;

    public LoginServlet(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public void init() throws ServletException {
        userList.add(new User("user", "user"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("login.ftl", new HashMap<>());
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean isLoggedIn = false;

        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                String token = UUID.randomUUID().toString();
                Cookie cookie = new Cookie("security-token", token);
                cookie.setMaxAge(10000);

                Session session = new Session();
                session.setToken(token);
                session.setUser(user);
                session.setExpireTime(LocalDateTime.now().plusSeconds(10000));

                sessionStore.addSession(session);

                resp.addCookie(cookie);

                isLoggedIn = true;
                break;
            }
        }

        if (!isLoggedIn) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
