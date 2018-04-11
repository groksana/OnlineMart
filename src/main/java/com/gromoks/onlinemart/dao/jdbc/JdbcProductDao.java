package com.gromoks.onlinemart.dao.jdbc;

import com.gromoks.onlinemart.dao.config.MyDataSource;
import com.gromoks.onlinemart.dao.mapper.ProductRowMapper;
import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.exception.ConnectionException;
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

    private static final String GET_ALL_PRODUCT_SQL = "SELECT ID, NAME, PRICE, PICTUREPATH, DESCRIPTION FROM PRODUCT";

    private static final String GET_PRODUCT_BY_ID_SQL = "SELECT ID, NAME, PRICE, PICTUREPATH, DESCRIPTION FROM PRODUCT WHERE ID = ?";

    private final ProductRowMapper productRowMapper = new ProductRowMapper();

    private MyDataSource myDataSource;

    public JdbcProductDao(MyDataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

    public List<Product> getAll() {
        log.info("Start query to get all product from DB");
        long startTime = System.currentTimeMillis();

        try (Connection connection = myDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = productRowMapper.mapRow(resultSet);
                products.add(product);
            }
            log.info("Finish query to get all product from DB. It took {} ms", System.currentTimeMillis() - startTime);
            return products;
        } catch (SQLException e) {
            log.error("Connection can't be established ", e);
            throw new ConnectionException("Connection can't be established ", e);
        }
    }

    public Product getById(int productId) {
        log.info("Start query to get product from DB by id = {}", productId);
        long startTime = System.currentTimeMillis();

        try (Connection connection = myDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID_SQL)) {

            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Product product = new Product();
            while (resultSet.next()) {
                product = productRowMapper.mapRow(resultSet);
            }

            log.info("Finish query to get product by id from DB. It took {} ms", System.currentTimeMillis() - startTime);
            return product;
        } catch (SQLException e) {
            log.error("Connection can't be established ", e);
            throw new ConnectionException("Connection can't be established ", e);
        }
    }
}
