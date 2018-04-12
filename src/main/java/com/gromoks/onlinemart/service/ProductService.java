package com.gromoks.onlinemart.service;

import com.gromoks.onlinemart.entity.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAll();

    public Product getById(int productId);
}
