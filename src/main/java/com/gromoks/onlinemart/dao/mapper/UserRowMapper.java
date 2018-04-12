package com.gromoks.onlinemart.dao.mapper;

import com.gromoks.onlinemart.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setLogin(resultSet.getString("email"));
        user.setPassword(resultSet.getString("role"));

        return user;
    }
}
