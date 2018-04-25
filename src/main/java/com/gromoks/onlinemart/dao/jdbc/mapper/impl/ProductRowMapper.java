package com.gromoks.onlinemart.dao.jdbc.mapper.impl;

import com.gromoks.onlinemart.dao.jdbc.mapper.RowMapper;
import com.gromoks.onlinemart.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setPicturePath(resultSet.getString("picturePath"));
        product.setDescription(resultSet.getString("description"));

        return product;
    }
}
