package com.gromoks.onlinemart.service;

import com.gromoks.onlinemart.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(int productId);

    int add(Product product);

    List<Product> search(String keyWord);
}
