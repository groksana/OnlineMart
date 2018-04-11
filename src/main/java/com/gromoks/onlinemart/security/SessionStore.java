package com.gromoks.onlinemart.security;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.security.entity.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SessionStore {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Session> sessionList = new ArrayList<>();

    public void addSession(Session session) {
        sessionList.add(session);
    }

    public boolean isValid(String token) {
        log.info("Start to check by user token = {}", token);

        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                return true;
            }
        }

        log.info("Finish to check by user token");
        return false;
    }

    public void addProductToSessionCart(String token, Product product) {
        log.info("Start to add product to session cart, product = {}", product);

        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                List<Product> products = session.getCart();
                if (products == null) {
                    products = new ArrayList<>();
                    session.setCart(products);
                }
                products.add(product);
            }
        }
        log.info("Finish to add product to session cart, product = {}", product);
    }

    public List<Product> getCartByToken(String token) {
        log.info("Start to get cart by token = {}", token);

        List<Product> products = null;
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                products = session.getCart();
            }
        }

        log.info("Finish to check cart by token");
        return products;
    }
}
