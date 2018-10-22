package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.User;
import com.torodavid.thesis.financialregister.dal.dto.UserDto;
import com.torodavid.thesis.financialregister.dal.repository.RoleRepository;
import com.torodavid.thesis.financialregister.dal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void register(UserDto userDto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setSurname(userDto.getSurname());
        user.setForename(userDto.getForename());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String id) {
        return new UserDto((userRepository.findById(id).get()));
    }

    @Override
    @Transactional
    public void update(UserDto userDto) {
        userRepository.setUserById(userDto.getId(), userDto.getEmail(), bCryptPasswordEncoder.encode(userDto.getPassword()), userDto.getSurname(), userDto.getForename());
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        return new UserDto(userRepository.findByUsername(username));
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserDto> findPaginated(PageRequest pageable, Optional<List<String>> ids) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<UserDto> list;

        List<UserDto> userDtos = new ArrayList<>();
        if (ids.isPresent()) {
            getAllUsers().forEach(userDto -> { if (ids.get().contains(userDto.getId())) userDtos.add(userDto); } );
        } else {
            getAllUsers().forEach(userDtos::add);
        }
        if (userDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, userDtos.size());
            list = userDtos.subList(startItem, toIndex);
        }

        Page<UserDto> cashFlowPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), userDtos.size());

        return cashFlowPage;
    }

    @Override
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUsername(userDetails.getUsername());
    }

}