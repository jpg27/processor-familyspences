package com.familyspences.processorfamilyapi.service.goals;

import com.familyspences.processorfamilyapi.domain.goals.Goals;
import com.familyspences.processorfamilyapi.repository.goals.GoalsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoalService {

    private static final Logger log = LoggerFactory.getLogger(GoalService.class);
    private final GoalsRepository repository;

    public GoalService(GoalsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveFromProducer(Goals goal) {
        log.info("Saving goal from producer: {}", goal);
        repository.save(goal);
    }

    @Transactional
    public void updateFromProducer(Goals updatedGoal) {
        try {
            UUID goalId = updatedGoal.getId();
            UUID categoryId = updatedGoal.getCategoryId();

            if (goalId == null || categoryId == null) {
                log.warn("Missing categoryId or id in update event: {}", updatedGoal);
                return;
            }

            Optional<Goals> existingOpt = repository.findByCategoryIdAndId(categoryId, goalId);
            if (existingOpt.isEmpty()) {
                log.warn("Goal not found for update. Category: {}, Goal: {}", categoryId, goalId);
                return;
            }

            Goals existing = existingOpt.get();
            existing.setName(updatedGoal.getName());
            existing.setDescription(updatedGoal.getDescription());
            existing.setCategoryId(updatedGoal.getCategoryId());
            existing.setSavingsCap(updatedGoal.getSavingsCap());
            existing.setDeadline(updatedGoal.getDeadline());
            existing.setDailyGoal(updatedGoal.getDailyGoal());

            repository.save(existing);
            log.info("Goal updated successfully: {} for category {}", goalId, categoryId);

        } catch (Exception e) {
            log.error("Error processing Goal UPDATE event: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteFromProducer(Map<String, String> data) {
        try {
            String categoryStr = data.get("categoryId");
            String goalStr = data.get("goalId");

            if (categoryStr == null || goalStr == null) {
                log.warn("Missing fields in DELETE event: {}", data);
                return;
            }

            UUID categoryId = UUID.fromString(categoryStr);
            UUID goalId = UUID.fromString(goalStr);

            if (repository.existsByCategoryIdAndId(categoryId, goalId)) {
                repository.deleteByCategoryIdAndId(categoryId, goalId);
                log.info("Goal deleted successfully: {} for category {}", goalId, categoryId);
            } else {
                log.warn("Goal with id {} not found for category {}", goalId, categoryId);
            }

        } catch (Exception e) {
            log.error("Error deleting goal from producer event: {}", e.getMessage(), e);
        }
    }
}