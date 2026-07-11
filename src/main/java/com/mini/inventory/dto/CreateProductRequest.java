package com.mini.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String category;

    @Positive
    private BigDecimal price;

    @PositiveOrZero
    private Integer availableQuantity;
}