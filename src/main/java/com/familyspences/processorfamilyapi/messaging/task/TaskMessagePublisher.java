package com.familyspences.processorfamilyapi.messaging.task;

import com.familyspences.processorfamilyapi.config.RabbitMQTaskConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskMessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(java.time.LocalDate.class,
                    (com.google.gson.JsonSerializer<java.time.LocalDate>) (date, type, jsonSerializationContext) ->
                            new com.google.gson.JsonPrimitive(date.toString()))
            .create();


    public TaskMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTaskCreated(Object task) {
        send(task, RabbitMQTaskConfig.TASK_EXCHANGE, RabbitMQTaskConfig.TASK_CREATE_ROUTING_KEY);
    }

    public void publishTaskUpdated(Object task) {
        send(task, RabbitMQTaskConfig.TASK_EXCHANGE, RabbitMQTaskConfig.TASK_UPDATE_ROUTING_KEY);
    }

    public void publishTaskDeleted(Object task) {
        send(task, RabbitMQTaskConfig.TASK_EXCHANGE, RabbitMQTaskConfig.TASK_DELETE_ROUTING_KEY);
    }

    private void send(Object message, String exchange, String routingKey) {
        String jsonMessage = gson.toJson(message);
        MessageProperties props = new MessageProperties();
        props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message amqpMessage = new Message(jsonMessage.getBytes(), props);
        rabbitTemplate.send(exchange, routingKey, amqpMessage);
        System.out.println("📤 [RabbitMQ] Enviado → " + routingKey + " | Mensaje: " + jsonMessage);
    }
}



