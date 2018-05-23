package com.gromoks.onlinemart.dao.jdbc;

import com.gromoks.jdbctemplate.NamedParameterJdbcTemplate;
import com.gromoks.onlinemart.dao.UserDao;
import com.gromoks.onlinemart.dao.jdbc.mapper.UserRowMapper;
import com.gromoks.onlinemart.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JdbcUserDao implements UserDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String GET_USER = " SELECT NICKNAME, EMAIL, ROLE FROM USERS WHERE email = :email and password = :password";

    private final UserRowMapper userRowMapper = new UserRowMapper();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String login, String password) {
        log.info("Start query to get user by email and password from db");
        long startTime = System.currentTimeMillis();

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("email", login);
        parameterMap.put("password", password);

        Optional<User> user;
        try {
            user = Optional.of(namedParameterJdbcTemplate.queryForObject(GET_USER, parameterMap, userRowMapper));
        } catch (SQLException e) {
            log.error("Issue during extract product by id from db ", e);
            throw new RuntimeException("Issue during extract product by id from db ", e);
        }

        log.info("Finish query to get user by email and password from DB. It took {} ms", System.currentTimeMillis() - startTime);
        return user;
    }
}
