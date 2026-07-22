package com.mini.inventory.controller;

import com.mini.inventory.client.PricingClient;
import com.mini.inventory.dto.PriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingClient pricingClient;

    @GetMapping("/{sku}")
    public PriceResponse getPrice(
            @PathVariable String sku) {

        return pricingClient.getPrice(sku);
    }
}