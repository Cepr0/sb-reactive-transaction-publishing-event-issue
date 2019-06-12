package io.github.cepr0.issue;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ModelService {

	private final ModelRepo repo;
	private final ApplicationEventPublisher publisher;

	public ModelService(final ModelRepo repo, final ApplicationEventPublisher publisher) {
		this.repo = repo;
		this.publisher = publisher;
	}

	@Transactional
	public Mono<Model> create(Model model, ModelEvent.Type type) {
		return repo.save(model)
				.doOnSuccess(m -> publisher.publishEvent(new ModelEvent(m, type)));
	}

	@Transactional
	public Flux<Model> getAll() {
		return repo.findAll();
	}

}
