package com.gromoks.onlinemart.security;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.security.entity.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.time.LocalDateTime.*;

public class SessionStore {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public void addSession(Session session) {
        sessionMap.put(session.getToken(), session);
    }

    public void removeSessionByToken(String token) {
        log.info("Start to remove session by user token = {}", token);
        sessionMap.remove(token);
        log.info("Finish to remove session by user token");
    }

    public boolean isValid(String token) {
        log.info("Start to validate user token = {}", token);

        Session session = sessionMap.get(token);
        if (session != null) {
            if (now().isAfter(session.getExpireTime())) {
                sessionMap.remove(token);
            } else {
                log.info("Validation of user token has been finished");
                return true;
            }
        }

        log.info("Validation of user token has been finished");
        return false;
    }

    public void addProductToSessionCart(String token, Product product) {
        log.info("Start to add product to session cart, product = {}", product);

        if (isValid(token)) {
            Session session = sessionMap.get(token);
            List<Product> products = session.getCart();
            if (products == null) {
                products = new ArrayList<>();
                session.setCart(products);
            }
            products.add(product);
        } else {
            log.error("Provided token is invalid: {}", token);
            throw new RuntimeException("Provided token is invalid, token = " + token);
        }

        log.info("Finish to add product to session cart, product = {}", product);
    }

    public List<Product> getCartByToken(String token) {
        log.info("Start to get cart by token = {}", token);

        Session session = sessionMap.get(token);
        List<Product> products;
        if (isValid(token)) {
            products = session.getCart();
        } else {
            log.error("Provided token is invalid: {}", token);
            throw new RuntimeException("Provided token is invalid, token = " + token);
        }

        log.info("Finish to get cart by token");
        return products;
    }

    public User getUserByToken(String token) {
        log.info("Start to get user by token = {}", token);

        Session session = sessionMap.get(token);
        User user;
        if (isValid(token)) {
            user = session.getUser();
        } else {
            log.error("Provided token is invalid: {}", token);
            throw new RuntimeException("Provided token is invalid, token = " + token);
        }

        log.info("Finish to get user by token");
        return user;
    }
}
