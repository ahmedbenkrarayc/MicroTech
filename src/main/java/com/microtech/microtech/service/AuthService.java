package com.microtech.microtech.service;

import com.microtech.microtech.dto.request.LoginRequest;
import com.microtech.microtech.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void logout();
}
