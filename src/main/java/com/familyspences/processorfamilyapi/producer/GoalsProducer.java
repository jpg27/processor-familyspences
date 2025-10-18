package com.familyspences.processorfamilyapi.producer;

import com.familyspences.processorfamilyapi.config.RabbitMQConfig;
import com.familyspences.processorfamilyapi.dto.goals.GoalMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class GoalsProducer {

    private static final Logger logger = LoggerFactory.getLogger(GoalsProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public GoalsProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendGoalMessage(String operation, GoalMessageDTO.GoalData data) {
        try {
            GoalMessageDTO message = new GoalMessageDTO(operation, data);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.GOALS_ROUTING_KEY,
                    message
            );

            logger.info("✅ Mensaje enviado: {} - Goal: {}",
                    operation, data.getName() != null ? data.getName() : data.getId());

        } catch (Exception e) {
            logger.error("❌ Error al enviar mensaje: {}", e.getMessage(), e);
            throw new RuntimeException("Error al enviar mensaje a RabbitMQ", e);
        }
    }
}