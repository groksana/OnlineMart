package com.gromoks.onlinemart.dao.jdbc;

import com.gromoks.onlinemart.dao.UserDao;
import com.gromoks.onlinemart.dao.config.MyDataSource;
import com.gromoks.onlinemart.dao.mapper.UserRowMapper;
import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.exception.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDao implements UserDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String GET_USER = " SELECT EMAIL, ROLE FROM USER WHERE email = ? and password = ?";

    private final UserRowMapper userRowMapper = new UserRowMapper();
    private MyDataSource myDataSource;

    public JdbcUserDao(MyDataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String login, String password) {
        log.info("Start query to get user by email and password from db");
        long startTime = System.currentTimeMillis();

        try (Connection connection = myDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<User> user = Optional.empty();
            while (resultSet.next()) {
                user = Optional.ofNullable(userRowMapper.mapRow(resultSet));
            }

            log.info("Finish query to get user by email and password from DB. It took {} ms", System.currentTimeMillis() - startTime);
            return user;
        } catch (SQLException e) {
            log.error("Connection can't be established ", e);
            throw new ConnectionException("Connection can't be established ", e);
        }

    }
}
