package com.gromoks.onlinemart.web.servlet.security;

import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.security.entity.Session;
import com.gromoks.onlinemart.service.UserService;
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
import java.time.LocalDateTime;
import java.util.*;

import static com.gromoks.onlinemart.security.util.PasswordEncryption.encryptPassword;
import static com.gromoks.onlinemart.web.util.RequestParser.checkAddProductState;

public class LoginServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private UserService userService;
    private SessionStore sessionStore;
    private String errorMessage;

    public LoginServlet(UserService userService, SessionStore sessionStore) {
        this.userService = userService;
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String addProductState = checkAddProductState(req);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        Map<String, Object> map = new HashMap<>();
        map.put("message", errorMessage);
        map.put("addProductState", addProductState);
        String page = thymeLeafPageGenerator.getHtmlPage("login", map);
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = encryptPassword(req.getParameter("password"));

        Optional<User> optionalUser = userService.getUserByEmailAndPassword(login, password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("security-token", token);
            cookie.setMaxAge(10000);

            Session session = new Session();
            session.setToken(token);
            session.setUser(user);
            session.setExpireTime(LocalDateTime.now().plusSeconds(10000));

            sessionStore.addSession(session);

            resp.addCookie(cookie);
            resp.sendRedirect("/products");
            log.info("User token {} for user {} has been set", token, user);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            errorMessage = "Login or password are incorrect. Please try again";
            resp.sendRedirect("/login");
            log.error("Login or password are incorrect for {}", login);
        }
    }
}
