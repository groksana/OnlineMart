package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.gromoks.onlinemart.web.entity.TemplateMode.*;

public class CartServlet extends HttpServlet {
    private SessionStore sessionStore;

    public CartServlet(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        Cookie[] cookies = req.getCookies();

        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if ("security-token".equals(cookieName)) {
                    if (sessionStore.isValid(cookie.getValue())) {
                        token = cookie.getValue();
                    }
                }
            }
        }

        List<Product> productCart = sessionStore.getCartByToken(token);
        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        String page = thymeLeafPageGenerator.getPage("cart", XHTML, "cart", productCart);
        writer.write(page);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
