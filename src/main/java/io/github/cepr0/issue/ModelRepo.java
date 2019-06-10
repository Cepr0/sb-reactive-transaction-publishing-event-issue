package io.github.cepr0.issue;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ModelRepo extends ReactiveMongoRepository<Model, String> {
}
