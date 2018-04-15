package com.gromoks.onlinemart.dao;

import com.gromoks.onlinemart.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();

    Product getById(int productId);

    int add(Product product);

    List<Product> search(String keyWord);
}
