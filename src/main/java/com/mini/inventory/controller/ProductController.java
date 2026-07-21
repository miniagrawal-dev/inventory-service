package com.mini.inventory.controller;

import com.mini.inventory.dto.CreateProductRequest;
import com.mini.inventory.dto.PageResponse;
import com.mini.inventory.dto.ProductResponse;
import com.mini.inventory.dto.UpdateProductRequest;
import com.mini.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(

            @Valid
            @RequestBody
            CreateProductRequest request){

        ProductResponse response =
                productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                productService.getProducts(page, size, sortBy, direction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id, @Valid @RequestBody UpdateProductRequest request){

        return ResponseEntity.ok(
                productService.updateProduct(id, request));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductResponse>>
    searchProducts(@RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")  int size,
                   @RequestParam(defaultValue = "id") String sortBy,
                   @RequestParam(defaultValue = "asc") String direction){

        return ResponseEntity.ok(
                productService.searchProducts(keyword, page, size, sortBy, direction));
    }

}