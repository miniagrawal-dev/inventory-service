package com.mini.inventory.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CircuitBreakerEventConfig {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void registerEventListeners() {

        CircuitBreaker circuitBreaker =
                circuitBreakerRegistry.circuitBreaker("pricingService");

        circuitBreaker.getEventPublisher()

                .onStateTransition(event ->
                        log.info("Circuit Breaker State Changed: {} -> {}",
                                event.getStateTransition().getFromState(),
                                event.getStateTransition().getToState()))

                .onFailureRateExceeded(event ->
                        log.warn("Failure rate exceeded: {}%",
                                event.getFailureRate()))

                .onCallNotPermitted(event ->
                        log.warn("Call blocked because circuit is OPEN"))

                .onError(event ->
                        log.error("Call failed"));

    }
}