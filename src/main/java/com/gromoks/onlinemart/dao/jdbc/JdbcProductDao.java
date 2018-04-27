package com.gromoks.onlinemart.dao.jdbc;

import com.gromoks.jdbctemplate.NamedParameterJdbcTemplate;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcProductDao implements ProductDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String GET_ALL_PRODUCT_SQL = "SELECT ID, NAME, PRICE, PICTUREPATH, DESCRIPTION FROM PRODUCT";

    private static final String GET_PRODUCT_BY_ID_SQL = "SELECT ID, NAME, PRICE, PICTUREPATH, DESCRIPTION FROM PRODUCT WHERE ID = :id";

    private static final String ADD_PRODUCT_SQL = "INSERT INTO PRODUCT(NAME, PRICE, PICTUREPATH, DESCRIPTION) VALUES(:name, :price, :picturepath, :description)";

    private static final String SEARCH_PRODUCT_BY_KEY_WORD = "SELECT ID, NAME, PRICE, PICTUREPATH, DESCRIPTION FROM PRODUCT WHERE NAME LIKE :name";

    private final ProductRowMapper productRowMapper = new ProductRowMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
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

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("id", productId);
        Product product = null;
        try {
            product = namedParameterJdbcTemplate.queryForObject(GET_PRODUCT_BY_ID_SQL, parameterMap, productRowMapper);
        } catch (SQLException e) {
            log.error("Issue during extract product by id from db ", e);
            throw new RuntimeException("Issue during extract product by id from db ", e);
        }

        log.info("Finish query to get product by id from DB. It took {} ms", System.currentTimeMillis() - startTime);
        return product;
    }

    @Override
    public int add(Product product) {
        log.info("Start query to add product {}", product);

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("name", product.getName());
        parameterMap.put("price", product.getPrice());
        parameterMap.put("picturepath", product.getPicturePath());
        parameterMap.put("description", product.getDescription());
        int key;
        try {
            key = namedParameterJdbcTemplate.update(ADD_PRODUCT_SQL, parameterMap);
        } catch (SQLException e) {
            log.error("Issue during product insert to db ", e);
            throw new RuntimeException("Issue during product insert to db ", e);
        }

        log.info("Finish query to add product with id - {}", key);
        return key;
    }

    @Override
    public List<Product> search(String keyWord) {
        log.info("Start query to search product from DB by keyWord = {}", keyWord);
        long startTime = System.currentTimeMillis();

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("name", "%" + keyWord + "%");
        List<Product> products;
        try {
            products = namedParameterJdbcTemplate.query(SEARCH_PRODUCT_BY_KEY_WORD, parameterMap, productRowMapper);
        } catch (SQLException e) {
            log.error("Issue during search products by key word in db ", e);
            throw new RuntimeException("Issue during search products by key word in db ", e);
        }

        log.info("Finish query to search product from DB by keyWord. It took {} ms", System.currentTimeMillis() - startTime);
        return products;
    }
}
