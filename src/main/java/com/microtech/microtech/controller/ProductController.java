package com.microtech.microtech.controller;

import com.microtech.microtech.dto.request.product.CreateProductRequest;
import com.microtech.microtech.dto.response.product.ProductResponse;
import com.microtech.microtech.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody CreateProductRequest request
    ) {
        ProductResponse response = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
