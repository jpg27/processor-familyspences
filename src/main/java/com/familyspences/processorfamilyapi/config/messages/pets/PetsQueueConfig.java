package com.familyspences.processorfamilyapi.config.messages.pets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetsQueueConfig {

    private static final Logger log = LoggerFactory.getLogger(PetsQueueConfig.class);

    public static final String EXCHANGE_NAME = "x.pet.exchange";

    public static final String QUEUE_PET_CREATE = "q.pet.create";
    public static final String QUEUE_PET_UPDATE = "q.pet.update";
    public static final String QUEUE_PET_DELETE = "q.pet.delete";

    public static final String ROUTING_KEY_CREATE = "pet.create";
    public static final String ROUTING_KEY_UPDATE = "pet.update";
    public static final String ROUTING_KEY_DELETE = "pet.delete";

    public PetsQueueConfig() {
        log.info("========================================");
        log.info("PetsQueueConfig INITIALIZED!");
        log.info("========================================");
    }

    // EXCHANGE
    @Bean
    public TopicExchange petExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    // QUEUES
    @Bean
    public Queue petCreateQueue() {
        return new Queue(QUEUE_PET_CREATE, true);
    }

    @Bean
    public Queue petUpdateQueue() {
        return new Queue(QUEUE_PET_UPDATE, true);
    }

    @Bean
    public Queue petDeleteQueue() {
        return new Queue(QUEUE_PET_DELETE, true);
    }

    // BINDINGS
    @Bean
    public Binding bindingCreate() {
        return BindingBuilder.bind(petCreateQueue())
                .to(petExchange())
                .with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding bindingUpdate() {
        return BindingBuilder.bind(petUpdateQueue())
                .to(petExchange())
                .with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindingDelete() {
        return BindingBuilder.bind(petDeleteQueue())
                .to(petExchange())
                .with(ROUTING_KEY_DELETE);
    }
}
