package com.microtech.microtech.dto.request.auth;

import com.microtech.microtech.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateClientRequest(
        @NotBlank(message = "First name is required!")
        @Size(min = 3, message = "First name must be at least 3 characters!")
        String fname,

        @NotBlank(message = "Last name is required!")
        @Size(min = 3, message = "Last name must be at least 3 characters!")
        String lname,

        @NotBlank(message = "Email is required!")
        @Email(message = "Email must be valid!")
        String email,

        @NotNull(message = "Role is required!")
        Role role,

        @NotBlank(message = "CIN is required")
        @Size(min = 4, max = 20, message = "CIN must be between 4 and 20 characters")
        String cin
) {
}
