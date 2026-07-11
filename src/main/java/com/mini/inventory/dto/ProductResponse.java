package com.mini.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String sku;

    private String name;

    private String category;

    private BigDecimal price;

    private Integer availableQuantity;
}