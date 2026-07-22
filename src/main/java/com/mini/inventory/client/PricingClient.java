package com.mini.inventory.client;

import com.mini.inventory.dto.PriceResponse;

public interface PricingClient {

    PriceResponse getPrice(String sku);
}