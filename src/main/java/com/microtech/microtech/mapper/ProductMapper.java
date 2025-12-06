package com.microtech.microtech.mapper;

import com.microtech.microtech.dto.request.product.CreateProductRequest;
import com.microtech.microtech.dto.response.product.ProductResponse;
import com.microtech.microtech.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(CreateProductRequest dto);
    ProductResponse toResponse(Product product);
}
