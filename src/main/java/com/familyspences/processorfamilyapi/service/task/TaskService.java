package com.familyspences.processorfamilyapi.service.task;

import com.familyspences.processorfamilyapi.domain.task.Tasks;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    public void validateTask(Tasks task) {
        if (task.getName() == null || task.getName().isBlank()) {
            throw new IllegalArgumentException("Task name is required");
        }
        if (task.getFamilyId() == null) {
            throw new IllegalArgumentException("FamilyId is required");
        }
    }

    public void validateIds(UUID familyId, UUID taskId) {
        if (familyId == null || taskId == null) {
            throw new IllegalArgumentException("FamilyId and TaskId are required");
        }
    }
}




