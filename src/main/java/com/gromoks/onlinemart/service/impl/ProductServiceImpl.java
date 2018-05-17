package com.gromoks.onlinemart.service.impl;

import com.gromoks.onlinemart.dao.ProductDao;
import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product getById(int productId) {
        return productDao.getById(productId);
    }

    @Override
    public int add(Product product) {
        return productDao.add(product);
    }

    @Override
    public List<Product> search(String keyWord) {
        return productDao.search(keyWord);
    }
}
