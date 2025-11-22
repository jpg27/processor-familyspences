package com.familyspences.processorfamilyapi.config.messages.vacation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VacationQueueConfig {

    private static final Logger log = LoggerFactory.getLogger(VacationQueueConfig.class);

    public static final String EXCHANGE_NAME = "x.vacation.exchange";

    public static final String QUEUE_VACATION_CREATE = "q.vacation.create";
    public static final String QUEUE_VACATION_UPDATE = "q.vacation.update";
    public static final String QUEUE_VACATION_DELETE = "q.vacation.delete";

    public static final String ROUTING_KEY_CREATE = "vacation.create";
    public static final String ROUTING_KEY_UPDATE = "vacation.update";
    public static final String ROUTING_KEY_DELETE = "vacation.delete";

    public VacationQueueConfig() {
        log.info("========================================");
        log.info("VacationQueueConfig INITIALIZED!");
        log.info("========================================");
    }

    // EXCHANGE
    @Bean
    public TopicExchange vacationExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    // QUEUES
    @Bean
    public Queue vacationCreateQueue() {
        return new Queue(QUEUE_VACATION_CREATE, true);
    }

    @Bean
    public Queue vacationUpdateQueue() {
        return new Queue(QUEUE_VACATION_UPDATE, true);
    }

    @Bean
    public Queue vacationDeleteQueue() {
        return new Queue(QUEUE_VACATION_DELETE, true);
    }

    // BINDINGS
    @Bean
    public Binding bindingVacationCreate() {
        return BindingBuilder.bind(vacationCreateQueue())
                .to(vacationExchange())
                .with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding bindingVacationUpdate() {
        return BindingBuilder.bind(vacationUpdateQueue())
                .to(vacationExchange())
                .with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindingVacationDelete() {
        return BindingBuilder.bind(vacationDeleteQueue())
                .to(vacationExchange())
                .with(ROUTING_KEY_DELETE);
    }
}
