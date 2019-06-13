package io.github.cepr0.issue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class Application {

    private final MongoTemplate mongoTemplate;

    public Application(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile("replSet")
    @Configuration
    public static class MongoClusterConfig {
        static {
            DockerComposeContainer cluster = new DockerComposeContainer(new File("docker-compose.yml"))
                    .withLocalCompose(true);
            cluster.start();
        }
    }

    @Bean
    ReactiveTransactionManager transactionManager(ReactiveMongoDatabaseFactory dbFactory) {
        return new ReactiveMongoTransactionManager(dbFactory);
    }

    @EventListener
    public void onReady(ApplicationReadyEvent event) {
        if (mongoTemplate.collectionExists(Model.class)) {
            mongoTemplate.dropCollection(Model.class);
        }
        mongoTemplate.createCollection(Model.class);
    }
}
