package com.familyspences.processorfamilyapi.config.messages.task;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskQueueConfig {

    public static final String EXCHANGE_NAME = "x.task.exchange";

    public static final String QUEUE_TASK_CREATE = "q.task.create";
    public static final String QUEUE_TASK_UPDATE = "q.task.update";
    public static final String QUEUE_TASK_DELETE = "q.task.delete";

    public static final String ROUTING_KEY_CREATE = "task.create";
    public static final String ROUTING_KEY_UPDATE = "task.update";
    public static final String ROUTING_KEY_DELETE = "task.delete";

    @Bean
    public DirectExchange taskExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue taskCreateQueue() {
        return new Queue(QUEUE_TASK_CREATE, true);
    }

    @Bean
    public Queue taskUpdateQueue() {
        return new Queue(QUEUE_TASK_UPDATE, true);
    }

    @Bean
    public Queue taskDeleteQueue() {
        return new Queue(QUEUE_TASK_DELETE, true);
    }

    @Bean
    public Binding bindCreate(Queue taskCreateQueue, DirectExchange taskExchange) {
        return BindingBuilder.bind(taskCreateQueue).to(taskExchange).with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding bindUpdate(Queue taskUpdateQueue, DirectExchange taskExchange) {
        return BindingBuilder.bind(taskUpdateQueue).to(taskExchange).with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindDelete(Queue taskDeleteQueue, DirectExchange taskExchange) {
        return BindingBuilder.bind(taskDeleteQueue).to(taskExchange).with(ROUTING_KEY_DELETE);
    }
}
