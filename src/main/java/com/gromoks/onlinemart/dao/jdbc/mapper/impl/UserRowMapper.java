package com.gromoks.onlinemart.dao.jdbc.mapper.impl;

import com.gromoks.onlinemart.dao.jdbc.mapper.RowMapper;
import com.gromoks.onlinemart.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.gromoks.onlinemart.security.entity.UserRole.*;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setNickname(resultSet.getString("nickname"));
        user.setLogin(resultSet.getString("email"));
        user.setRole(getByName(resultSet.getString("role")));

        return user;
    }
}
