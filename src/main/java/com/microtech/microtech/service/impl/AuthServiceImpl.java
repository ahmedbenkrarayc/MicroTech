package com.microtech.microtech.service.impl;

import com.microtech.microtech.dto.request.LoginRequest;
import com.microtech.microtech.dto.response.LoginResponse;
import com.microtech.microtech.model.User;
import com.microtech.microtech.repository.UserRepository;
import com.microtech.microtech.security.exception.UnauthorizedException;
import com.microtech.microtech.security.service.PasswordService;
import com.microtech.microtech.security.service.SessionService;
import com.microtech.microtech.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final SessionService sessionService;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Wrong email or password !"));

        boolean matched = passwordService.matches(request.password() ,user.getPassword());
        if(!matched) {
            throw new UnauthorizedException("Wrong email or password !");
        }

        sessionService.setUser(user.getId(), user.getRole().name());

        return LoginResponse.builder()
                .message("User Logged in successfully")
                .userId(user.getId())
                .role(user.getRole().name())
                .build();
    }
}
