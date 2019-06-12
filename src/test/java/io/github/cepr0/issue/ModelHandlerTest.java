package io.github.cepr0.issue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import static io.github.cepr0.issue.ModelEvent.Type.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class ModelHandlerTest {

    @SpyBean private ModelHandler handler;
    @Autowired private ModelService service;

    private final Model model = new Model("model");

    @Test
    void handleNormalEvent() {
        service.create(model, NORMAL).block();
        verify(handler, times(1)).handleNormalEvent(any(ModelEvent.class));
    }

    @Test
    void handleEventWithException() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> service.create(model, WITH_EXCEPTION).block())
                .withMessage("Oops, something went wrong...");
    }

    @Test
    void handleEventWithTransactional() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> service.create(model, WITH_TRANSACTIONAL).block())
                .withMessageStartingWith("Specified transaction manager is not a PlatformTransactionManager");
    }

    @Test
    void handleEventInTransactionEventListener() {
        service.create(model, IN_TRANSACTIONAL_EVENT_LISTENER).block();
        verify(handler, times(0)).handleEventInTransactionEventListener(any(ModelEvent.class));
    }
}