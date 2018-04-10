package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.service.ProductService;
import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ProductServlet extends HttpServlet {

    private ProductService productService;

    public ProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String[] uris=req.getRequestURI().split("/");
        int productId = Integer.valueOf(uris[2]);

        Product product = productService.getById(productId);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        String page = thymeLeafPageGenerator.getPage("product", "product", product);

        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
