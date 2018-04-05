package com.gromoks.onlinemart.security;

import com.gromoks.onlinemart.security.entity.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionStore {
    private List<Session> sessionList = new ArrayList<>();

    public void addSession(Session session) {
        sessionList.add(session);
    }

    public boolean checkByToken(String token) {
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }
}
