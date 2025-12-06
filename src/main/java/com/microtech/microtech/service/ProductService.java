package com.microtech.microtech.service;

import com.microtech.microtech.dto.request.product.CreateProductRequest;
import com.microtech.microtech.dto.request.product.UpdateProductRequest;
import com.microtech.microtech.dto.response.product.ProductResponse;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);
    ProductResponse update(Long id, UpdateProductRequest request);
}
