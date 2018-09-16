package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.User;
import com.torodavid.thesis.financialregister.dal.dto.UserDto;

public interface UserService {

    void register(UserDto userDto);
    User findByUsername(String username);
    Iterable<UserDto> getAllUsers();
}