package com.microtech.microtech.controller;

import com.microtech.microtech.dto.request.product.CreateProductRequest;
import com.microtech.microtech.dto.request.product.UpdateProductRequest;
import com.microtech.microtech.dto.response.product.ProductResponse;
import com.microtech.microtech.security.annotation.RolesAllowed;
import com.microtech.microtech.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RolesAllowed(roles = {"ADMIN"})
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody CreateProductRequest request
    ) {
        ProductResponse response = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @RolesAllowed(roles = {"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        ProductResponse response = productService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @RolesAllowed(roles = {"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @RolesAllowed(roles = {"ADMIN"})
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProductsFiletered(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
            productService.getProductsFiltered(id, name, page, size)
        );
    }
}
