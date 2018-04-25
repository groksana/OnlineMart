package com.gromoks.onlinemart.dao.jdbc;

import com.gromoks.onlinemart.dao.UserDao;
import com.gromoks.onlinemart.dao.jdbc.config.DataSource;
import com.gromoks.onlinemart.dao.jdbc.mapper.UserRowMapper;
import com.gromoks.onlinemart.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDao implements UserDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String GET_USER = " SELECT NICKNAME, EMAIL, ROLE FROM USERS WHERE email = ? and password = ?";

    private final UserRowMapper userRowMapper = new UserRowMapper();
    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String login, String password) {
        log.info("Start query to get user by email and password from db");
        long startTime = System.currentTimeMillis();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<User> user = Optional.empty();
            if (resultSet.next()) {
                user = Optional.of(userRowMapper.mapRow(resultSet));
            } else if (resultSet.next()) {
                log.error("Constraint violation. We can't have several users per email");
                throw new RuntimeException("Constraint violation. We can't have several users per email");
            }

            log.info("Finish query to get user by email and password from DB. It took {} ms", System.currentTimeMillis() - startTime);
            return user;
        } catch (SQLException e) {
            log.error("Issue during extract user by email and password ", e);
            throw new RuntimeException("Issue during extract user by email and password ", e);
        }

    }
}
