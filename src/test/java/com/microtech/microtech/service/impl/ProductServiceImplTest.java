package com.microtech.microtech.service.impl;

import com.microtech.microtech.dto.request.product.CreateProductRequest;
import com.microtech.microtech.dto.request.product.UpdateProductRequest;
import com.microtech.microtech.dto.response.product.ProductResponse;
import com.microtech.microtech.exception.ResourceNotFoundException;
import com.microtech.microtech.exception.UniqueResourceException;
import com.microtech.microtech.mapper.ProductMapper;
import com.microtech.microtech.model.Product;
import com.microtech.microtech.repository.OrderItemRepository;
import com.microtech.microtech.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private CreateProductRequest createRequest;
    private UpdateProductRequest updateRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .stock(100)
                .price(99.99)
                .description("Test product description")
                .deleted(false)
                .build();

        createRequest = new CreateProductRequest(
                "New Product",
                50,
                49.99,
                "New product description with at least 10 chars"
        );

        updateRequest = new UpdateProductRequest(
                "Updated Product",
                75,
                89.99,
                "Updated product description with at least 10 chars"
        );

        productResponse = new ProductResponse(
                1L,
                "Test Product",
                100,
                99.99,
                "Test product description"
        );
    }

    @Test
    void create_ShouldCreateProductSuccessfully_WhenProductDoesNotExist() {
        when(productRepository.findByNameIgnoreCase(createRequest.name()))
                .thenReturn(Optional.empty());
        when(productMapper.toEntity(createRequest)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.create(createRequest);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Product", result.name());

        verify(productRepository).findByNameIgnoreCase(createRequest.name());
        verify(productRepository).save(product);
        verify(productMapper).toEntity(createRequest);
        verify(productMapper).toResponse(product);
    }

    @Test
    void create_ShouldThrowUniqueResourceException_WhenProductNameAlreadyExists() {
        when(productRepository.findByNameIgnoreCase(createRequest.name()))
                .thenReturn(Optional.of(product));

        UniqueResourceException exception = assertThrows(UniqueResourceException.class,
                () -> productService.create(createRequest));

        assertEquals("This product already exists!", exception.getMessage());

        verify(productRepository).findByNameIgnoreCase(createRequest.name());
        verify(productRepository, never()).save(any());
        verify(productMapper, never()).toEntity(any());
        verify(productMapper, never()).toResponse(any());
    }

    @Test
    void update_ShouldUpdateProductSuccessfully_WhenProductExists() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productMapper).updateEntityFromDto(updateRequest, product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.update(productId, updateRequest);

        assertNotNull(result);

        verify(productRepository).findById(productId);
        verify(productMapper).updateEntityFromDto(updateRequest, product);
        verify(productMapper).toResponse(product);
    }

    @Test
    void update_ShouldThrowResourceNotFoundException_WhenProductDoesNotExist() {
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.update(productId, updateRequest));

        assertEquals("Product not found with id : 999", exception.getMessage());

        verify(productRepository).findById(productId);
        verify(productMapper, never()).updateEntityFromDto(any(), any());
        verify(productMapper, never()).toResponse(any());
    }

    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenProductDoesNotExist() {
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.delete(productId));

        assertEquals("Product not found with id : 999", exception.getMessage());

        verify(productRepository).findById(productId);
        verify(orderItemRepository, never()).existsByProductId(any());
        verify(productRepository, never()).deleteById(any());
    }

    @Test
    void getProductsFiltered_ShouldReturnFilteredProductsPage() {
        Long productId = 1L;
        String productName = "Test";
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = Arrays.asList(product);
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAllProductsFiltered(productId, productName, pageable))
                .thenReturn(productPage);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        Page<ProductResponse> result = productService.getProductsFiltered(productId, productName, page, size);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(productResponse, result.getContent().get(0));

        verify(productRepository).findAllProductsFiltered(productId, productName, pageable);
        verify(productMapper).toResponse(product);
    }

    @Test
    void getProductsFiltered_ShouldReturnEmptyPage_WhenNoProductsMatchCriteria() {
        Long productId = null;
        String productName = "NonExistent";
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> emptyPage = Page.empty(pageable);

        when(productRepository.findAllProductsFiltered(productId, productName, pageable))
                .thenReturn(emptyPage);

        Page<ProductResponse> result = productService.getProductsFiltered(productId, productName, page, size);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());

        verify(productRepository).findAllProductsFiltered(productId, productName, pageable);
        verify(productMapper, never()).toResponse(any());
    }

    @Test
    void create_ShouldHandleNullValuesGracefully() {
        CreateProductRequest nullRequest = new CreateProductRequest(
                null,
                0,
                0.0,
                null
        );

        when(productRepository.findByNameIgnoreCase(null)).thenReturn(Optional.empty());
        when(productMapper.toEntity(nullRequest)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.create(nullRequest);

        assertNotNull(result);

        verify(productRepository).findByNameIgnoreCase(null);
        verify(productRepository).save(product);
        verify(productMapper).toEntity(nullRequest);
        verify(productMapper).toResponse(product);
    }

    @Test
    void update_ShouldHandlePartialUpdatesCorrectly() {
        Long productId = 1L;
        UpdateProductRequest partialUpdateRequest = new UpdateProductRequest(
                "Partial Update",
                -1,
                0.0,
                "Short"
        );

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productMapper).updateEntityFromDto(partialUpdateRequest, product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.update(productId, partialUpdateRequest);

        assertNotNull(result);

        verify(productRepository).findById(productId);
        verify(productMapper).updateEntityFromDto(partialUpdateRequest, product);
        verify(productMapper).toResponse(product);
    }

    @Test
    void create_ShouldSaveProductWithCorrectData() {
        when(productRepository.findByNameIgnoreCase(createRequest.name()))
                .thenReturn(Optional.empty());
        when(productMapper.toEntity(createRequest)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.create(createRequest);

        assertNotNull(result);

        verify(productRepository).save(product);
        verify(productMapper).toEntity(createRequest);
    }
}