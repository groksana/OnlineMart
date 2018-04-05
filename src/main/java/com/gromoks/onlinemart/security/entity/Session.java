package com.gromoks.onlinemart.security.entity;

import com.gromoks.onlinemart.entity.Product;
import com.gromoks.onlinemart.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class Session {
    private String token;
    private LocalDateTime expireTime;
    private User user;
    private List<Product> cart;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Session{" +
                "token='" + token + '\'' +
                ", expireTime=" + expireTime +
                ", user=" + user +
                '}';
    }
}
