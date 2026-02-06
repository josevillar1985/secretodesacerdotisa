package com.secretodesacerdotisa.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "sacerdotisa2026";
        String hash = encoder.encode(password);

        System.out.println("TU HASH ES:");
        System.out.println(hash);
    }
}
