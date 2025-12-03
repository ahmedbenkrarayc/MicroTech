package com.microtech.microtech.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordEncoder passwordEncoder;

    public String hash(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matches(String password, String hashed){
        return passwordEncoder.matches(password, hashed);
    }
}
