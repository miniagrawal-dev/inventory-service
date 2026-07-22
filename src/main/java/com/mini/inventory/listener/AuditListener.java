package com.mini.inventory.listener;

import com.mini.inventory.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class AuditListener {

    @Async("inventoryAsyncExecutor")
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleProductCreated(ProductCreatedEvent event) {

        log.info(
                "AUDIT :: Product {} created on thread {}",
                event.getProduct().getSku(),
                Thread.currentThread().getName());

        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}