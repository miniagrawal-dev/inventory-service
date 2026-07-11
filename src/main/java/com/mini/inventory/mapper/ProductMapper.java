package com.mini.inventory.mapper;

import com.mini.inventory.dto.CreateProductRequest;
import com.mini.inventory.dto.ProductResponse;
import com.mini.inventory.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(CreateProductRequest request);

    ProductResponse toResponse(Product product);
}
