package com.microtech.microtech.dto.request.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateProductRequest(
        @NotBlank(message = "Product name is required!")
        @Size(min = 3, message = "Product name must be at least 3 characters!")
        String name,
        @Min(value = 0, message = "Stock cannot be negative!")
        int stock,
        @Positive(message = "Price must be greater than 0!")
        double price,
        @Size(min = 10, message = "Description must be at least 10 characters!")
        String description
) {
}
