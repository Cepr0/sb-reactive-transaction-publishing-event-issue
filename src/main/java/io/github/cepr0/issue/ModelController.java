package io.github.cepr0.issue;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("models")
public class ModelController {

	private final ModelService service;

	public ModelController(final ModelService service) {
		this.service = service;
	}

	@PostMapping
	public Mono<Model> create(@RequestBody Model model) {
		return service.create(model);
	}

	@GetMapping
	public Flux<Model> getAll() {
		return service.getAll();
	}
}
