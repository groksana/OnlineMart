package com.gromoks.onlinemart.dao;

import com.gromoks.onlinemart.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> getUserByEmailAndPassword(String login, String password);
}
