package com.gromoks.onlinemart.service.impl;

import com.gromoks.onlinemart.dao.UserDao;
import com.gromoks.onlinemart.entity.User;
import com.gromoks.onlinemart.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String login, String password) {
        return userDao.getUserByEmailAndPassword(login, password);
    }
}
