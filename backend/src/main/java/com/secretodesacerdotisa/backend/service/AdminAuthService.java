package com.secretodesacerdotisa.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    @Value("${admin.password.hash}")
    private String adminPasswordHash;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean checkPassword(String password) {
        return encoder.matches(password, adminPasswordHash);
    }
}
