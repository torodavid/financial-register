package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}