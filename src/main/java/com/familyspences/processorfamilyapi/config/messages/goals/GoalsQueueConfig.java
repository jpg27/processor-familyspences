package com.familyspences.processorfamilyapi.config.messages.goals;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoalsQueueConfig {

    public static final String EXCHANGE_NAME = "x.goal.exchange";

    public static final String QUEUE_GOAL_CREATE = "q.goal.create";
    public static final String QUEUE_GOAL_UPDATE = "q.goal.update";
    public static final String QUEUE_GOAL_DELETE = "q.goal.delete";

    public static final String ROUTING_KEY_CREATE = "goal.create";
    public static final String ROUTING_KEY_UPDATE = "goal.update";
    public static final String ROUTING_KEY_DELETE = "goal.delete";

    @Bean
    public DirectExchange goalExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue goalCreateQueue() {
        return new Queue(QUEUE_GOAL_CREATE, true);
    }

    @Bean
    public Queue goalUpdateQueue() {
        return new Queue(QUEUE_GOAL_UPDATE, true);
    }

    @Bean
    public Queue goalDeleteQueue() {
        return new Queue(QUEUE_GOAL_DELETE, true);
    }

    @Bean
    public Binding bindGoalCreate(Queue goalCreateQueue, DirectExchange goalExchange) {
        return BindingBuilder.bind(goalCreateQueue).to(goalExchange).with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding bindGoalUpdate(Queue goalUpdateQueue, DirectExchange goalExchange) {
        return BindingBuilder.bind(goalUpdateQueue).to(goalExchange).with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindGoalDelete(Queue goalDeleteQueue, DirectExchange goalExchange) {
        return BindingBuilder.bind(goalDeleteQueue).to(goalExchange).with(ROUTING_KEY_DELETE);
    }
}