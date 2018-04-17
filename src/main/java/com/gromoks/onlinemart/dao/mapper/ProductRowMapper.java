package com.gromoks.onlinemart.dao.mapper;

import com.gromoks.onlinemart.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {
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
