package com.gromoks.onlinemart.dao.jdbc;

import com.gromoks.onlinemart.dao.config.JdbcConnection;
import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.handler.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private JdbcConnection jdbcConnection;

    private static final String GET_ALL_PRODUCT_SQL = "SELECT NAME, PRICE, PICTUREPATH FROM PRODUCT";

    public JdbcProductDao(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    public List<Product> getAllProduct() {
        log.info("Start query to get all product from DB");
        long startTime = System.currentTimeMillis();

        List<Product> products;
        try (Connection connection = jdbcConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT_SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                products = new ArrayList<>();

                while (resultSet.next()) {
                    Product product = new Product();
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setPicturePath(resultSet.getString("picturePath"));

                    products.add(product);
                }
            }
        } catch (SQLException e) {
            log.error("Connection can't be established ", e);
            throw new ConnectionException("Connection can't be established " + e.getMessage());
        }

        log.info("Finish query to get all product from DB. It took {} ms", System.currentTimeMillis() - startTime);
        return products;
    }
}
