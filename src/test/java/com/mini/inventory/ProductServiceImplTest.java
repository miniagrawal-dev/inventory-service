package com.mini.inventory;

import com.mini.inventory.dto.CreateProductRequest;
import com.mini.inventory.dto.ProductResponse;
import com.mini.inventory.entity.Product;
import com.mini.inventory.event.ProductCreatedEvent;
import com.mini.inventory.exception.DuplicateSkuException;
import com.mini.inventory.mapper.ProductMapper;
import com.mini.inventory.repository.ProductRepository;
import com.mini.inventory.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ProductServiceImpl service;

    private CreateProductRequest request;
    private Product product;
    private Product savedProduct;
    private ProductResponse response;

    @BeforeEach
    void setup() {
        request = CreateProductRequest.builder()
                        .sku("SKU-1")
                        .name("Laptop")
                        .category("Electronics")
                        .price(BigDecimal.valueOf(50000))
                        .availableQuantity(5)
                        .build();

        product = Product.builder()
                        .sku("SKU-1")
                        .name("Laptop")
                        .category("Electronics")
                        .price(BigDecimal.valueOf(50000))
                        .availableQuantity(5)
                        .build();

        savedProduct = Product.builder()
                        .id(1L)
                        .sku("SKU-1")
                        .name("Laptop")
                        .category("Electronics")
                        .price(BigDecimal.valueOf(50000))
                        .availableQuantity(5)
                        .build();

        response = ProductResponse.builder()
                        .id(1L)
                        .sku("SKU-1")
                        .name("Laptop")
                        .category("Electronics")
                        .price(BigDecimal.valueOf(50000))
                        .availableQuantity(5)
                        .build();
    }

    @Test
    public void shouldCreateProductSuccessfully() {
        when(repository.existsBySku("SKU-1")).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(product);
        when(repository.save(product)).thenReturn(savedProduct);
        when(mapper.toResponse(savedProduct)).thenReturn(response);

        ProductResponse actual =
                service.createProduct(request);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
        assertEquals("SKU-1", actual.getSku());
        assertEquals("Laptop", actual.getName());

        verify(repository).existsBySku("SKU-1");
        verify(mapper).toEntity(request);
        verify(repository).save(product);
        verify(mapper).toResponse(savedProduct);
        verify(eventPublisher)
                .publishEvent(any(ProductCreatedEvent.class));
    }

    @Test
    @DisplayName("Should throw DuplicateSkuException when SKU already exists")
    public void shouldThrowDuplicateSkuExceptionWhenSkuAlreadyExists() {
        // Arrange
        given(repository.existsBySku(request.getSku()))
                .willReturn(true);

        // Act & Assert
        DuplicateSkuException exception = assertThrows(
                DuplicateSkuException.class,
                () -> service.createProduct(request)
        );

        assertEquals(
                "Product with SKU " + request.getSku() + "already exists",
                exception.getMessage()
        );

        // Verify interactions
        verify(repository).existsBySku(request.getSku());

        verify(repository, never()).save(any(Product.class));
        verify(mapper, never()).toEntity(any(CreateProductRequest.class));
        verify(mapper, never()).toResponse(any(Product.class));
        verify(eventPublisher, never()).publishEvent(any());

        verifyNoMoreInteractions(repository, mapper, eventPublisher);
    }
}
