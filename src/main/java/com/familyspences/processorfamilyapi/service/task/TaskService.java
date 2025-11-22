package com.familyspences.processorfamilyapi.service.task;

import com.familyspences.processorfamilyapi.config.messages.task.TaskDTO;
import com.familyspences.processorfamilyapi.domain.task.Tasks;
import com.familyspences.processorfamilyapi.repository.task.ITaskRepository;
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

    public TaskService(ITaskRepository repository) {
        this.repository = repository;
    }

    @Transactional
        public void saveFromProducer(TaskDTO taskDTO) {
            log.info("💾 Guardando task desde producer...");

            Tasks task = new Tasks();
            task.setId(taskDTO.getId());
            task.setFamilyId(taskDTO.getFamilyId());
            task.setName(taskDTO.getName());
            task.setDescription(taskDTO.getDescription());
            task.setStatus(taskDTO.isStatus());
            task.setCreationDate(taskDTO.getCreationDate());
            task.setIdResponsible(taskDTO.getIdResponsible());

            repository.save(task);
            log.info("✅ Task guardada: {}", task.getId());
        }


    @Transactional
    public void updateFromProducer(TaskDTO taskDTO) {
        log.info("🔄 Actualizando task desde producer...");
        try {
            UUID taskId = taskDTO.getId();
            UUID familyId = taskDTO.getFamilyId();

            if (taskId == null || familyId == null) {
                log.warn("⚠️ Missing familyId or id in update event: {}", taskDTO);
                return;
            }

            Optional<Tasks> existingOpt = repository.findByFamilyIdAndId(familyId, taskId);
            if (existingOpt.isEmpty()) {
                log.warn("⚠️ Task not found for update. Family: {}, Task: {}", familyId, taskId);
                return;
            }

            Tasks existing = existingOpt.get();
            existing.setName(taskDTO.getName());
            existing.setDescription(taskDTO.getDescription());
            existing.setStatus(taskDTO.isStatus());
            existing.setCreationDate(taskDTO.getCreationDate());
            existing.setIdExpenseve(taskDTO.getIdExpenseve());
            existing.setIdVacations(taskDTO.getIdVacations());
            existing.setIdResponsible(taskDTO.getIdResponsible());

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
                log.warn(" Missing fields in DELETE event: {}", data);
                return;
            }

            UUID familyId = UUID.fromString(familyStr);
            UUID taskId = UUID.fromString(taskStr);

            if (repository.existsByFamilyIdAndId(familyId, taskId)) {
                repository.deleteByFamilyIdAndId(familyId, taskId);
                log.info("Task deleted successfully: {} for family {}", taskId, familyId);
            } else {
                log.warn(" Task with id {} not found for family {}", taskId, familyId);
            }

        } catch (Exception e) {
            log.error(" Error deleting task from producer event: {}", e.getMessage(), e);
        }
    }


}
