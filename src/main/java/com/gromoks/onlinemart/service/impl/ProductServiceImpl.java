package com.gromoks.onlinemart.service.impl;

import com.gromoks.onlinemart.dao.ProductDao;
import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao jdbcProductDao;

    public ProductServiceImpl(ProductDao jdbcProductDao) {
        this.jdbcProductDao = jdbcProductDao;
    }

    @Override
    public List<Product> getAll() {
        return jdbcProductDao.getAll();
    }

    @Override
    public Product getById(int productId) {
        return jdbcProductDao.getById(productId);
    }
}
