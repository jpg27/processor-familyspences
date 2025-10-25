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

    // ... resto del código igual
}