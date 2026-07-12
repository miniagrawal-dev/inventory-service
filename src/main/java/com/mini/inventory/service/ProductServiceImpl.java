package com.mini.inventory.service;

import com.mini.inventory.dto.CreateProductRequest;
import com.mini.inventory.dto.ProductResponse;
import com.mini.inventory.entity.Product;
import com.mini.inventory.exception.DuplicateSkuException;
import com.mini.inventory.exception.ProductNotFoundException;
import com.mini.inventory.mapper.ProductMapper;
import com.mini.inventory.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl
        implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating product with SKU={}",
                request.getSku());

        if(repository.existsBySku(request.getSku())) {

            throw new DuplicateSkuException(
                    request.getSku());

        }

      /*  Using builder

      Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .availableQuantity(
                        request.getAvailableQuantity())
                .reservedQuantity(0)

                .build();
       */

        //Using mapStruct
        Product product = mapper.toEntity(request);

        Product saved = repository.save(product);

        log.info(
                "Product created. sku={}, id={}, category={}",
                product.getSku(),
                product.getId(),
                product.getCategory()
        );

       /* Using Builder
         return ProductResponse.builder()
                .id(saved.getId())
                .sku(saved.getSku())
                .name(saved.getName())
                .category(saved.getCategory())
                .price(saved.getPrice())
                .availableQuantity(
                        saved.getAvailableQuantity())

                .build();
        */

        return mapper.toResponse(saved);

    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(()->
                        new ProductNotFoundException(id));
        return mapper.toResponse(product);
    }
}