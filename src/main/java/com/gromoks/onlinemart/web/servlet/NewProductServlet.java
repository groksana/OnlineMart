package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewProductServlet extends HttpServlet {

    private ProductService productService;
    private SessionStore sessionStore;

    public NewProductServlet(ProductService productService, SessionStore sessionStore) {
        this.productService = productService;
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String picturePath = req.getParameter("image");
        double price = Double.valueOf(req.getParameter("price"));
        String description = req.getParameter("description");
    }
}
