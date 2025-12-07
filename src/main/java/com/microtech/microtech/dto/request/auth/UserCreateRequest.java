package com.microtech.microtech.dto.request.auth;

import com.microtech.microtech.model.enums.Role;

public interface UserCreateRequest {
    String fname();
    String lname();
    String email();
    String password();
    Role role();
}
