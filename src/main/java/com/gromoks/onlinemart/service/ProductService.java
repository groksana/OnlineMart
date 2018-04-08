package com.gromoks.onlinemart.service;

import com.gromoks.onlinemart.dao.jdbc.JdbcProductDao;
import com.gromoks.onlinemart.entity.Product;

import java.util.List;

public class ProductService {
    private JdbcProductDao jdbcProductDao;

    public ProductService(JdbcProductDao jdbcProductDao) {
        this.jdbcProductDao = jdbcProductDao;
    }

    public List<Product> getAll() {
        return jdbcProductDao.getAll();
    }

    public Product getById(int productId) {
        return jdbcProductDao.getById(productId);
    }
}
