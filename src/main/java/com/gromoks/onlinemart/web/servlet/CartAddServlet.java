package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.service.ProductService;
import com.gromoks.onlinemart.security.SessionStore;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartAddServlet extends HttpServlet {

    private ProductService productService;

    private SessionStore sessionStore;

    public CartAddServlet(ProductService productService, SessionStore sessionStore) {
        this.productService = productService;
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uris = req.getRequestURI().split("/");
        int productId = Integer.valueOf(uris[2]);
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

        Product product = productService.getById(productId);
        sessionStore.addProductToSessionCart(token, product);
        resp.sendRedirect("/product/" + productId);
    }
}
