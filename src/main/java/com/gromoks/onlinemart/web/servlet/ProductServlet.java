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
import java.util.HashMap;
import java.util.Map;

import static com.gromoks.onlinemart.web.entity.TemplateMode.HTML;
import static com.gromoks.onlinemart.web.util.RequestParser.checkAddProductState;

public class ProductServlet extends HttpServlet {

    private ProductService productService;
    private SessionStore sessionStore;

    public ProductServlet(ProductService productService, SessionStore sessionStore) {
        this.productService = productService;
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uris=req.getRequestURI().split("/");
        int productId = Integer.valueOf(uris[2]);

        Product product = productService.getById(productId);
        String addProductState = checkAddProductState(req, sessionStore);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("addProductState", addProductState);
        String page = thymeLeafPageGenerator.getPage("product", HTML, map);
        resp.setCharacterEncoding( "UTF-8" );
        PrintWriter writer = resp.getWriter();
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
