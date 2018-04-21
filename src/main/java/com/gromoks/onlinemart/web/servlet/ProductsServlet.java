package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.service.ProductService;
import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.gromoks.onlinemart.web.util.RequestParser.checkAddProductState;

public class ProductsServlet extends HttpServlet {

    private ProductService productService;
    private SessionStore sessionStore;

    public ProductsServlet(ProductService productService, SessionStore sessionStore) {
        this.productService = productService;
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        List<Product> productList = productService.getAll();
        String addProductState = checkAddProductState(req, sessionStore);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        Map<String, Object> map = new HashMap<>();
        map.put("products", productList);
        map.put("addProductState", addProductState);
        String page = thymeLeafPageGenerator.getHtmlPage("products", map);
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
