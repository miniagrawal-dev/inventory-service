package com.mini.inventory.service;

import com.mini.inventory.dto.CreateProductRequest;
import com.mini.inventory.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(
            CreateProductRequest request);

}
