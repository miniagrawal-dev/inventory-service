package com.mini.inventory.listener;

import com.mini.inventory.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class EmailListener {

    @Async("inventoryAsyncExecutor")
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void sendEmail(ProductCreatedEvent event) {

        log.info(
                "EMAIL :: Sending email for {} on thread {}",
                event.getProduct().getSku(),
                Thread.currentThread().getName());

        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}