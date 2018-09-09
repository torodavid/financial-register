package com.torodavid.thesis.financialregister.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);

}