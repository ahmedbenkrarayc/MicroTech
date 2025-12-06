package com.microtech.microtech.dto.response.auth;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long userId,
        String role,
        String message
) {
}
