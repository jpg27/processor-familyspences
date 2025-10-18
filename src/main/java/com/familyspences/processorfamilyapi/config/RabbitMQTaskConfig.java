package com.familyspences.processorfamilyapi.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTaskConfig {

    public static final String TASK_EXCHANGE = "x.task.events";

    public static final String TASK_CREATE_ROUTING_KEY = "event.task.create";
    public static final String TASK_UPDATE_ROUTING_KEY = "event.task.update";
    public static final String TASK_DELETE_ROUTING_KEY = "event.task.delete";

    public static final String TASK_CREATE_QUEUE = "task.create.queue";
    public static final String TASK_UPDATE_QUEUE = "task.update.queue";
    public static final String TASK_DELETE_QUEUE = "task.delete.queue";

    @Bean
    public TopicExchange taskExchange() {
        return new TopicExchange(TASK_EXCHANGE, true, false);
    }

    @Bean
    public Queue taskCreateQueue() {
        return new Queue(TASK_CREATE_QUEUE, true);
    }

    @Bean
    public Queue taskUpdateQueue() {
        return new Queue(TASK_UPDATE_QUEUE, true);
    }

    @Bean
    public Queue taskDeleteQueue() {
        return new Queue(TASK_DELETE_QUEUE, true);
    }

    @Bean
    public Binding bindingCreate(Queue taskCreateQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskCreateQueue).to(taskExchange).with(TASK_CREATE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingUpdate(Queue taskUpdateQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskUpdateQueue).to(taskExchange).with(TASK_UPDATE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingDelete(Queue taskDeleteQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskDeleteQueue).to(taskExchange).with(TASK_DELETE_ROUTING_KEY);
    }
}
