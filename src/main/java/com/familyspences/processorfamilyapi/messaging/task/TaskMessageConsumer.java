package com.familyspences.processorfamilyapi.messaging.task;

import com.familyspences.processorfamilyapi.config.RabbitMQTaskConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaskMessageConsumer {

    @RabbitListener(queues = RabbitMQTaskConfig.TASK_CREATE_QUEUE)
    public void receiveTaskCreated(String message) {
        System.out.println("========================================");
        System.out.println("📥 Mensaje recibido desde RabbitMQ:");
        System.out.println("Cola: " + RabbitMQTaskConfig.TASK_CREATE_QUEUE);
        System.out.println("Contenido: " + message);
        System.out.println("========================================");
    }
}