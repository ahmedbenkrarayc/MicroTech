package com.microtech.microtech.dto.response.product;

public record ProductResponse(
        Long id,
        String name,
        int stock,
        double price,
        String description
) {
}
