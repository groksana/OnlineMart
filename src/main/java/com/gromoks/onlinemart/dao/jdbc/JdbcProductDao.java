package com.gromoks.onlinemart.dao.jdbc;

import com.gromoks.onlinemart.dao.ProductDao;
import com.gromoks.onlinemart.dao.jdbc.config.DataSource;
import com.gromoks.onlinemart.dao.jdbc.mapper.ProductRowMapper;
import com.gromoks.onlinemart.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String GET_ALL_PRODUCT_SQL = "SELECT ID, NAME, PRICE, PICTUREPATH, DESCRIPTION FROM PRODUCT";

    private static final String GET_PRODUCT_BY_ID_SQL = "SELECT ID, NAME, PRICE, PICTUREPATH, DESCRIPTION FROM PRODUCT WHERE ID = ?";

    private static final String ADD_PRODUCT_SQL = "INSERT INTO PRODUCT(NAME, PRICE, PICTUREPATH, DESCRIPTION) VALUES(?, ?, ?, ?)";

    private static final String SEARCH_PRODUCT_BY_KEY_WORD = "SELECT ID, NAME, PRICE, PICTUREPATH, DESCRIPTION FROM PRODUCT WHERE NAME LIKE ?";

    private final ProductRowMapper productRowMapper = new ProductRowMapper();

    private DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        log.info("Start query to get all product from DB");
        long startTime = System.currentTimeMillis();

        try (Connection connection = dataSource.getConnection();
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
            log.error("Issue during extract all product from db ", e);
            throw new RuntimeException("Issue during extract all product from db ", e);
        }
    }

    @Override
    public Product getById(int productId) {
        log.info("Start query to get product from DB by id = {}", productId);
        long startTime = System.currentTimeMillis();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID_SQL)) {

            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Product product = new Product();
            if (resultSet.next()) {
                product = productRowMapper.mapRow(resultSet);
            } else if (resultSet.next()) {
                log.error("Constraint violation. We can't have several products per one id");
                throw new RuntimeException("Constraint violation. We can't have several products per one id");
            }

            log.info("Finish query to get product by id from DB. It took {} ms", System.currentTimeMillis() - startTime);
            return product;
        } catch (SQLException e) {
            log.error("Issue during extract product by id from db ", e);
            throw new RuntimeException("Issue during extract product by id from db ", e);
        }
    }

    @Override
    public int add(Product product) {
        log.info("Start query to add product {}", product);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getPicturePath());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int key = resultSet.next() ? resultSet.getInt(1) : 0;

            log.info("Finish query to add product");
            return key;
        } catch (SQLException e) {
            log.error("Issue during product insert to db ", e);
            throw new RuntimeException("Issue during product insert to db ", e);
        }
    }

    @Override
    public List<Product> search(String keyWord) {
        log.info("Start query to search product from DB by keyWord = {}", keyWord);
        long startTime = System.currentTimeMillis();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PRODUCT_BY_KEY_WORD)) {

            preparedStatement.setString(1, "%" + keyWord + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            log.info("Final statement {}", preparedStatement);

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productRowMapper.mapRow(resultSet);
                products.add(product);
            }

            log.info("Finish query to search product from DB by keyWord. It took {} ms", System.currentTimeMillis() - startTime);
            return products;
        } catch (SQLException e) {
            log.error("Issue during search product by key word from db ", e);
            throw new RuntimeException("Issue during search product by key word from db ", e);
        }
    }
}
