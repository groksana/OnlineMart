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
import java.util.ArrayList;
import java.util.List;

import static com.gromoks.onlinemart.web.entity.TemplateMode.XHTML;

public class ProductsServlet extends HttpServlet {

    private ProductService productService;
    private List<Product> productList = new ArrayList<>();

    public ProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void init() throws ServletException {
        productList = productService.getAll();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        String page = thymeLeafPageGenerator.getPage("products", XHTML, "products", productList);
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
