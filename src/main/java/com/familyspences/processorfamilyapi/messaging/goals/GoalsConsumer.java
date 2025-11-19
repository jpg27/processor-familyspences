package com.familyspences.processorfamilyapi.messaging.goals;

import com.familyspences.processorfamilyapi.config.messages.goals.GoalDTO;
import com.familyspences.processorfamilyapi.config.messages.goals.GoalsQueueConfig;
import com.familyspences.processorfamilyapi.domain.goals.Goals;
import com.familyspences.processorfamilyapi.service.goals.GoalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GoalsConsumer {

    private static final Logger log = LoggerFactory.getLogger(GoalsConsumer.class);
    private final GoalService goalService;

    public GoalsConsumer(GoalService goalService) {
        this.goalService = goalService;
    }

    @RabbitListener(queues = GoalsQueueConfig.QUEUE_GOAL_CREATE)
    public void handleGoalCreate(GoalDTO goalDTO) {
        log.info("Received Goal CREATE event: {}", goalDTO.getId());
        try {
            goalService.saveFromProducer(goalDTO);
            log.info("Goal saved successfully: {}", goalDTO.getId());
        } catch (Exception e) {
            log.error("Error processing Goal CREATE event. Goal ID: {}. Error: {}",
                    goalDTO.getId(), e.getMessage(), e);
        }
    }

    @RabbitListener(queues = GoalsQueueConfig.QUEUE_GOAL_UPDATE)
    public void handleGoalUpdate(GoalDTO goalDTO) {
        log.info("Received Goal UPDATE event: {}", goalDTO.getId());
        goalService.updateFromProducer(goalDTO);
    }

    @RabbitListener(queues = GoalsQueueConfig.QUEUE_GOAL_DELETE)
    public void handleGoalDelete(Map<String, String> data) {
        log.info("Received Goal DELETE event: {}", data);
        goalService.deleteFromProducer(data);
    }
}