package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.service.ProductService;
import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.gromoks.onlinemart.web.util.RequestParser.checkAddProductState;

public class NewProductServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private ProductService productService;
    private String errorMessage;

    public NewProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String addProductState = checkAddProductState(req);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        Map<String, Object> map = new HashMap<>();
        map.put("message", errorMessage);
        map.put("addProductState", addProductState);
        String page = thymeLeafPageGenerator.getHtmlPage("newproduct", map);
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        try {
            product.setName(req.getParameter("name"));
            product.setPrice(Double.valueOf(req.getParameter("price")));
            product.setPicturePath(req.getParameter("image"));
            product.setDescription(req.getParameter("description"));

            int productId = productService.add(product);
            resp.sendRedirect("/product/" + productId);
        } catch (Exception e) {
            errorMessage = "Product can't be save. Please try again";
            log.error("Product can't be save {}", product);
            resp.sendRedirect("/newproduct");
            throw new RuntimeException("Incorrect input data for new product");
        }
    }
}
