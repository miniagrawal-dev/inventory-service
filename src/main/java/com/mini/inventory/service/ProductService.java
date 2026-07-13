package com.mini.inventory.service;

import com.mini.inventory.dto.CreateProductRequest;
import com.mini.inventory.dto.PageResponse;
import com.mini.inventory.dto.ProductResponse;
import com.mini.inventory.dto.UpdateProductRequest;

public interface ProductService {

    ProductResponse createProduct(
            CreateProductRequest request);

    ProductResponse getProduct(Long id);

    PageResponse<ProductResponse> getProducts(int page, int size, String sortBy, String direction);

    ProductResponse updateProduct(Long id, UpdateProductRequest request);

}
