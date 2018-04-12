package com.gromoks.onlinemart.service;

import com.gromoks.onlinemart.entity.User;

public interface UserService {
    User getUserByEmailAndPassword(String login, String password);
}
