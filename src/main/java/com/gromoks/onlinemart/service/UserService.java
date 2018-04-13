package com.gromoks.onlinemart.service;

import com.gromoks.onlinemart.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmailAndPassword(String login, String password);
}
