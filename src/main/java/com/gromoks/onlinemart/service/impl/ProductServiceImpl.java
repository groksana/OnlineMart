package com.gromoks.onlinemart.service.impl;

import com.gromoks.onlinemart.dao.jdbc.JdbcProductDao;
import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private JdbcProductDao jdbcProductDao;

    public ProductServiceImpl(JdbcProductDao jdbcProductDao) {
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
