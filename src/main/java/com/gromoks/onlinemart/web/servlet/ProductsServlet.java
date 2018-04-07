package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsServlet extends HttpServlet {

    private List<Product> productList = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        productList.add(new Product("iPhone7", 20000));
        productList.add(new Product("Стол", 2000));
        productList.add(new Product("Samsung", 10000));
        productList.add(new Product("Sony", 2000));
        productList.add(new Product("Lenovo", 2000));
        productList.add(new Product("iPhone8", 10000));
        productList.add(new Product("iPhoneX", 2000));
        productList.add(new Product("Nokia", 2000));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        PageGenerator pageGenerator = PageGenerator.instance();

        Map<String, Object> map = new HashMap<>();
        map.put("products", productList);
        String page = pageGenerator.getPage("products.ftl", map);
        writer.write(page);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
