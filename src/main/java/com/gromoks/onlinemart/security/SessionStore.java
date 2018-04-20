package com.gromoks.onlinemart.security;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.security.entity.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.time.LocalDateTime.*;

public class SessionStore {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Session> sessionList = new ArrayList<>();

    public void addSession(Session session) {
        sessionList.add(session);
    }

    public void removeSessionByToken(String token) {
        log.info("Start to remove session by user token = {}", token);
        Iterator<Session> iterator = sessionList.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getToken().equals(token)) {
                iterator.remove();
            }
        }
        log.info("Finish to remove session by user token");
    }

    public boolean isValid(String token) {
        log.info("Start to validate user token = {}", token);

        Iterator<Session> iterator = sessionList.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getToken().equals(token)) {
                if (now().isAfter(session.getExpireTime())) {
                    iterator.remove();
                } else {
                    return true;
                }
            }
        }

        log.info("Validation of user token has been finished");
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

        log.info("Finish to get cart by token");
        return products;
    }

    public User getUserByToken(String token) {
        log.info("Start to get user by token = {}", token);

        User user = null;
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                user = session.getUser();
            }
        }

        log.info("Finish to get user by token");
        return user;
    }
}
