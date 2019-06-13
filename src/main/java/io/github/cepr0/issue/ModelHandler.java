package io.github.cepr0.issue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ModelHandler {

    @EventListener(condition = "#event.normal()")
    public void handleNormalEvent(ModelEvent event) {
        log.info("[i] Handled event: {}", event.getModel());
    }

    @EventListener(condition = "#event.withException()")
    public void handleEventWithException(ModelEvent event) {
        log.info("[i] Handled event: {}, with exception", event.getModel());
        throw new RuntimeException("Oops, something went wrong...");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @EventListener(condition = "#event.withTransactional()")
    public void handleEventWithTransactional(ModelEvent event) {
        log.info("[i] Handled event: {}, with transactional", event.getModel());
    }

    @Async
    @TransactionalEventListener(condition = "#event.inTransactionEventListener()")
    public void handleEventInTransactionEventListener(ModelEvent event) {
        log.info("[i] Handled event: {}, in transactional event listener", event.getModel());
    }
}
