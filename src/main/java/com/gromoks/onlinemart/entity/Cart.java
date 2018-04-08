package com.gromoks.onlinemart.entity;

import java.util.List;

public class Cart {
    private User user;
    private List<Product> products;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                ", products=" + products +
                '}';
    }
}
