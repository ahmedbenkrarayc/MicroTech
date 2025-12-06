package com.microtech.microtech.service;

import com.microtech.microtech.dto.request.product.CreateProductRequest;
import com.microtech.microtech.dto.request.product.UpdateProductRequest;
import com.microtech.microtech.dto.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);
    ProductResponse update(Long id, UpdateProductRequest request);
    void delete(Long id);
    Page<ProductResponse> getProductsFiltered(Long id, String name, int page, int size);
}
