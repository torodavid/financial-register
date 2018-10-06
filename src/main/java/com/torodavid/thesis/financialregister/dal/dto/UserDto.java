package com.torodavid.thesis.financialregister.dal.dto;

import com.torodavid.thesis.financialregister.dal.dao.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

    private String id;
    @NotNull
    @NotEmpty
    @Size(min=4, max=20)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min=5, max=10)
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    public UserDto() {
    }

    public UserDto(@NotNull @NotEmpty String id, @NotNull @NotEmpty String username, @NotNull @NotEmpty String email, String password, String matchingPassword) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public UserDto(User user) {
        this(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPasswordConfirm());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}