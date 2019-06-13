This demo demonstrates the issue with the behavior of the 'event listener' methods 
when events are published within the transactional reactive methods 
in Spring Boot 2.2.0.M3.

**Sample reactive method**

```java
@Transactional
public Mono<Model> create(Model model, ModelEvent.Type type) {
    return repo.save(model)
            .doOnSuccess(m -> publisher.publishEvent(new ModelEvent(m, type)));
}
```

**Normal behavior**

The following code works as expected. The method is invoked within the current transaction -
if we uncomment the throwing the exception then the current transaction will be rolled back.

```java
@EventListener(condition = "#event.normal()")
public void handleNormalEvent(ModelEvent event) {
    log.info("[i] Handled event: {}", event.getModel());
    // throw new RuntimeException("Oops, something went wrong...");
}
```  

**IllegalStateException is raised**

If we add `@Transactional` annotation with `MANDATORY` propagation parameter to check that 
the 'event listener' method is invoked in the existent transaction, 
then the `IllegalStateException` is raised with a message: 
`Specified transaction manager is not a PlatformTransactionManager: 
org.springframework.data.mongodb.ReactiveMongoTransactionManager`  

```java
@Transactional(propagation = Propagation.MANDATORY)
@EventListener(condition = withTransactional)
public void handleEventWithTransactional(ModelEvent event) {
    log.info("[i] Handled event: {}, with transactional", event.getModel());
}
```

**@TransactionalEventListener**

If we use `@TransactionalEventListener` (instead of `@EventListener`) then 
the 'event listener' method is not invoked at all.   

```java
// @Async
@TransactionalEventListener(condition = "#event.inTransactionEventListener()")
public void handleEventInTransactionEventListener(ModelEvent event) {
    log.info("[i] Handled event: {}, in transactional event listener", event.getModel());
}
```       

**Reproducing the issue**

1) Start a MongoDB Replica Set (within the project folder)
```bash
docker-compose up -d
```

2) Start the application
```bash
mvn spring-boot:run
```

3) Run the requests from the `demo-requests.http` file 

(or you can run the application and requests within Intellij IDEA)

4) Don't forget to stop the cluster ;)
```bash
docker-compose down
```

Or you can check the [`ModelHandlerTest`](src/test/java/io/github/cepr0/issue/ModelHandlerTest.java) class.