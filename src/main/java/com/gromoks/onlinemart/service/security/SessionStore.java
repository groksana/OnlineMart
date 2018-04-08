package com.gromoks.onlinemart.service.security;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.service.security.entity.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionStore {
    private List<Session> sessionList = new ArrayList<>();

    public void addSession(Session session) {
        sessionList.add(session);
    }

    public boolean checkByToken(String token) {
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }

    public void addProductToSessionCart(String token, Product product) {
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                List<Product> products = session.getCart();
                if (products == null) {
                    products = new ArrayList<>();
                }
                products.add(product);
                session.setCart(products);
            }
        }
    }

    public List<Product> getCartByToken(String token) {
        List<Product> products = null;
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                products = session.getCart();
            }
        }
        return products;
    }
}
