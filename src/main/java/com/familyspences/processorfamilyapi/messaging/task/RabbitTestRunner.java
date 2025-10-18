//package com.familyspences.processorfamilyapi.messaging.task;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//
//@Component
//public class RabbitTestRunner implements CommandLineRunner {
//
//    private final RabbitTemplate rabbitTemplate;
//
//    public RabbitTestRunner(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    @Override
//    public void run(String... args) {
//        rabbitTemplate.convertAndSend("x.task.events", "event.task.create", "Test desde producer!");
//        System.out.println("✅ Test enviado manualmente a RabbitMQ.");
//    }
//}
//
