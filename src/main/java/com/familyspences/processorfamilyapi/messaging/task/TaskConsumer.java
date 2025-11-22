package com.familyspences.processorfamilyapi.messaging.task;

import com.familyspences.processorfamilyapi.config.messages.task.TaskQueueConfig;
import com.familyspences.processorfamilyapi.config.messages.task.TaskDTO;
import com.familyspences.processorfamilyapi.service.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaskConsumer {

    private static final Logger log = LoggerFactory.getLogger(TaskConsumer.class);
    private final TaskService taskService;

    public TaskConsumer(TaskService taskService) {
        this.taskService = taskService;
    }

    @RabbitListener(queues = TaskQueueConfig.QUEUE_TASK_CREATE)
    public void handleTaskCreate(TaskDTO taskDTO){
        log.info("✅ Received Task CREATE event: {}", taskDTO.getId());
        try {
            taskService.saveFromProducer(taskDTO);
            log.info("✅ Task saved successfully: {}", taskDTO.getId());
        } catch (Exception e) {
            log.error("❌ Error processing Task CREATE event. Task ID: {}. Error: {}",
                    taskDTO.getId(), e.getMessage(), e);
        }
    }

    @RabbitListener(queues = TaskQueueConfig.QUEUE_TASK_UPDATE)
    public void handleTaskUpdate(TaskDTO taskDTO) {
        log.info("✅ Received Task UPDATE event: {}", taskDTO.getId());
        taskService.updateFromProducer(taskDTO);
    }

    @RabbitListener(queues = TaskQueueConfig.QUEUE_TASK_DELETE)
    public void handleTaskDelete(Map<String, String> data) {
        log.info("✅ Received Task DELETE event: {}", data);
        taskService.deleteFromProducer(data);
    }
}