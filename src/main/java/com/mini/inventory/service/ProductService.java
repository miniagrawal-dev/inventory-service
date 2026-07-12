package com.mini.inventory.service;

import com.mini.inventory.dto.CreateProductRequest;
import com.mini.inventory.dto.PageResponse;
import com.mini.inventory.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(
            CreateProductRequest request);

    ProductResponse getProduct(Long id);

    PageResponse<ProductResponse> getProducts(int page, int size, String sortBy, String direction);

}
