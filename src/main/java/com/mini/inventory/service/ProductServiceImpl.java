package com.mini.inventory.service;

import com.mini.inventory.dto.CreateProductRequest;
import com.mini.inventory.dto.PageResponse;
import com.mini.inventory.dto.ProductResponse;
import com.mini.inventory.dto.UpdateProductRequest;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProducts(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = repository.findAll(pageable);

        return PageResponse.<ProductResponse>builder()
                .content(
                        productPage.getContent()
                                .stream()
                                .map(mapper::toResponse)
                                .toList()
                )
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .first(productPage.isFirst())
                .last(productPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id));

        mapper.updateProduct(request, product);
        log.info("Updated product {}", id);

        return mapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> searchProducts(String keyword, int page, int size,
                                                        String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Product> productPage =
                repository
                        .findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                                keyword,
                                keyword,
                                pageable);

        return PageResponse.<ProductResponse>builder()
                .content(productPage
                        .getContent()
                        .stream()
                        .map(mapper::toResponse)
                        .toList())
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .first(productPage.isFirst())
                .last(productPage.isLast())
                .build();
    }
}