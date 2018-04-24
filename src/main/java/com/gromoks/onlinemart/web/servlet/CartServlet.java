package com.gromoks.onlinemart.web.servlet;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.security.SessionStore;
import com.gromoks.onlinemart.web.templater.ThymeLeafPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gromoks.onlinemart.web.util.RequestParser.*;

public class CartServlet extends HttpServlet {
    private SessionStore sessionStore;

    public CartServlet(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String token = getSecurityToken(req, sessionStore);

        List<Product> productCart = sessionStore.getCartByToken(token);
        String addProductState = checkAddProductState(req);

        ThymeLeafPageGenerator thymeLeafPageGenerator = ThymeLeafPageGenerator.instance();
        Map<String, Object> map = new HashMap<>();
        map.put("cart", productCart);
        map.put("addProductState", addProductState);
        String page = thymeLeafPageGenerator.getHtmlPage("cart", map);
        writer.write(page);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
