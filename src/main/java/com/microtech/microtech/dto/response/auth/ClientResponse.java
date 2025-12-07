package com.microtech.microtech.dto.response.auth;

import com.microtech.microtech.model.enums.Role;

public record ClientResponse(
        Long id,
        String fname,
        String lname,
        String email,
        Role role,
        String cin
) {
}
