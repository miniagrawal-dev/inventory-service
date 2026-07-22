package com.mini.inventory.client;

import com.mini.inventory.dto.PriceResponse;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class PricingClientImpl implements PricingClient {

    private final Random random = new Random();

    @Override
    @Retry(
            name = "pricingService",
            fallbackMethod = "fallbackPrice"
    )
    @CircuitBreaker(
            name = "pricingService",
            fallbackMethod = "fallbackPrice"
    )
    @RateLimiter(
            name = "pricingService",
            fallbackMethod = "rateLimitFallback"
    )
    @Bulkhead(
            name = "pricingService",
            fallbackMethod = "bulkheadFallback"
    )
    public PriceResponse getPrice(String sku) {

        log.info("Calling Pricing Service...");

//        if (random.nextBoolean()) {
//            throw new RuntimeException("Pricing Service Down");
//        }

        return PriceResponse.builder()
                .sku(sku)
                .price(BigDecimal.valueOf(999))
                .currency("INR")
                .build();
    }

    public PriceResponse fallbackPrice(
            String sku,
            RuntimeException ex) {

        log.warn(
                "Fallback executed because Pricing Service failed. Reason={}",
                ex.getMessage());

        return PriceResponse.builder()
                .sku(sku)
                .price(BigDecimal.ZERO)
                .currency("INR")
                .build();
    }

    public PriceResponse rateLimitFallback(
            String sku,
            RequestNotPermitted ex) {

        log.warn("Rate limit exceeded for sku={}", sku);

        return PriceResponse.builder()
                .sku(sku)
                .price(BigDecimal.ZERO)
                .currency("INR")
                .build();
    }

    public PriceResponse bulkheadFallback(
            String sku,
            BulkheadFullException ex) {

        log.warn("Bulkhead is full for sku={}", sku);

        return PriceResponse.builder()
                .sku(sku)
                .price(BigDecimal.ZERO)
                .currency("INR")
                .build();
    }
}