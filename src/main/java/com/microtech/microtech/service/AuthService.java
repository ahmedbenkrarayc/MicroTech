package com.microtech.microtech.service;

import com.microtech.microtech.dto.request.auth.LoginRequest;
import com.microtech.microtech.dto.response.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void logout();
}
