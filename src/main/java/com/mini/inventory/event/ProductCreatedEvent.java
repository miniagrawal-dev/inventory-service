package com.mini.inventory.event;

import com.mini.inventory.dto.ProductResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductCreatedEvent {

    private final ProductResponse product;
}