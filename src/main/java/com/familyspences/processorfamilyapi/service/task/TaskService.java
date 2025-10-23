package com.familyspences.processorfamilyapi.service.task;

import com.familyspences.processorfamilyapi.domain.task.Tasks;
import com.familyspences.processorfamilyapi.repository.task.ITaskRepository;
import com.familyspences.processorfamilyapi.utils.gson.MapperJsonObject;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final ITaskRepository repository;
    private final MapperJsonObject mapperJsonObject;

    public TaskService(ITaskRepository repository, MapperJsonObject mapperJsonObject) {
        this.repository = repository;
        this.mapperJsonObject = mapperJsonObject;
    }

    @Transactional
    public void saveFromProducer(Tasks task) {
        log.info("💾 Saving task from producer: {}", task);
        repository.save(task);
    }

    @Transactional
    public void updateFromProducer(Tasks updatedTask) {
        try {
            UUID taskId = updatedTask.getId();
            UUID familyId = updatedTask.getFamilyId();

            if (taskId == null || familyId == null) {
                log.warn("⚠️ Missing familyId or id in update event: {}", updatedTask);
                return;
            }

            Optional<Tasks> existingOpt = repository.findByFamilyIdAndId(familyId, taskId);
            if (existingOpt.isEmpty()) {
                log.warn("⚠️ Task not found for update. Family: {}, Task: {}", familyId, taskId);
                return;
            }

            Tasks existing = existingOpt.get();
            existing.setName(updatedTask.getName());
            existing.setDescription(updatedTask.getDescription());
            existing.setStatus(updatedTask.isStatus());
            existing.setCreationDate(updatedTask.getCreationDate());
            existing.setIdExpenseve(updatedTask.getIdExpenseve());
            existing.setIdResponsible(updatedTask.getIdResponsible());

            repository.save(existing);
            log.info("✅ Task updated successfully: {} for family {}", taskId, familyId);

        } catch (Exception e) {
            log.error("❌ Error processing Task UPDATE event: {}", e.getMessage(), e);
        }
    }


    @Transactional
    public void deleteFromProducer(Map<String, String> data) {
        try {
            String familyStr = data.get("familyId");
            String taskStr = data.get("taskId");

            if (familyStr == null || taskStr == null) {
                log.warn("⚠️ Missing fields in DELETE event: {}", data);
                return;
            }

            UUID familyId = UUID.fromString(familyStr);
            UUID taskId = UUID.fromString(taskStr);

            // Si tu repositorio tiene un método específico:
            if (repository.existsByFamilyIdAndId(familyId, taskId)) {
                repository.deleteByFamilyIdAndId(familyId, taskId);
                log.info("🗑️ Task deleted successfully: {} for family {}", taskId, familyId);
            } else {
                log.warn("⚠️ Task with id {} not found for family {}", taskId, familyId);
            }

        } catch (Exception e) {
            log.error("❌ Error deleting task from producer event: {}", e.getMessage(), e);
        }
    }


}
