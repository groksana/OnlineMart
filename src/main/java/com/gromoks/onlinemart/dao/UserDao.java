package com.gromoks.onlinemart.dao;

import com.gromoks.onlinemart.entity.User;

public interface UserDao {
    User getUserByEmailAndPassword(String login, String password);
}
