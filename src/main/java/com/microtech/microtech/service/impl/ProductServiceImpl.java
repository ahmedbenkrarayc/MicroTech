package com.microtech.microtech.service.impl;

import com.microtech.microtech.dto.request.product.CreateProductRequest;
import com.microtech.microtech.dto.response.product.ProductResponse;
import com.microtech.microtech.exception.UniqueResourceException;
import com.microtech.microtech.mapper.ProductMapper;
import com.microtech.microtech.model.Product;
import com.microtech.microtech.repository.ProductRepository;
import com.microtech.microtech.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse create(CreateProductRequest request) {
        if(productRepository.findByNameIgnoreCase(request.name()).isPresent()) {
            throw new UniqueResourceException("This product already exists!");
        }

        Product product = productMapper.toEntity(request);

        Product saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }
}
