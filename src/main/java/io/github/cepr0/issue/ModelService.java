package io.github.cepr0.issue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ModelService {

	private final ModelRepo repo;
	private final ApplicationEventPublisher publisher;

	public ModelService(final ModelRepo repo, final ApplicationEventPublisher publisher) {
		this.repo = repo;
		this.publisher = publisher;
	}

	@Transactional
	public Mono<Model> create(Model model) {
		return repo.save(model)
				.doOnSuccess(m -> publisher.publishEvent(new ModelCreatedEvent(m)));
	}

	@Transactional
	public Flux<Model> getAll() {
		return repo.findAll();
	}

//	@Async
//	@TransactionalEventListener
//	@Transactional(propagation = Propagation.MANDATORY)
	@EventListener
	public void handleCreateEvent(ModelCreatedEvent event) {
		log.info("[i] Handled 'Model created' event: {}", event.getModel());
		throw new RuntimeException("oops!");
	}
}
