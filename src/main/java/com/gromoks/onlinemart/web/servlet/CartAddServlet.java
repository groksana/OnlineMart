package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.service.ProductService;
import com.gromoks.onlinemart.security.SessionStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gromoks.onlinemart.web.util.RequestParser.*;

public class CartAddServlet extends HttpServlet {

    private ProductService productService;

    private SessionStore sessionStore;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSessionStore(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uris = req.getRequestURI().split("/");
        int productId = Integer.valueOf(uris[2]);

        String token = getSecurityToken(req, sessionStore);

        Product product = productService.getById(productId);
        sessionStore.addProductToSessionCart(token, product);
        resp.sendRedirect("/product/" + productId);
    }
}
