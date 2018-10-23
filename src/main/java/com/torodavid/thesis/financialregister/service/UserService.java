package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.User;
import com.torodavid.thesis.financialregister.dal.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void register(UserDto userDto);
    User findByUsername(String username);
    Iterable<UserDto> getAllUsers();
    UserDto getUserById(String id);
    void update(UserDto userDto);
    UserDto getUserDtoByUsername(String username);
    void deleteById(String id);
    Page<UserDto> findPaginated(PageRequest pageable, Optional<List<String>> ids);
    User getCurrentUser();
    boolean validateEmail(UserDto userDto);
    boolean validateUsername(UserDto userDto);
}
