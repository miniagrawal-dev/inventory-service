package com.mini.inventory.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse  {

    private Long id;

    private String sku;

    private String name;

    private String category;

    private BigDecimal price;

    private Integer availableQuantity;
}