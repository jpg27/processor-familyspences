package com.familyspences.processorfamilyapi.service.goals;

import com.familyspences.processorfamilyapi.domain.goals.Goals;
import com.familyspences.processorfamilyapi.config.messages.goals.GoalDTO;
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
    public void saveFromProducer(GoalDTO goalDTO) {
        log.info("Saving goal from producer: {}", goalDTO.getId());

        Goals goal = new Goals();
        goal.setId(goalDTO.getId());
        goal.setFamilyId(goalDTO.getFamilyId());
        goal.setCategoryId(goalDTO.getCategoryId());
        goal.setName(goalDTO.getName());
        goal.setDescription(goalDTO.getDescription());
        goal.setSavingsCap(goalDTO.getSavingsCap());
        goal.setDeadline(goalDTO.getDeadline());
        goal.setDailyGoal(goalDTO.getDailyGoal());

        repository.save(goal);
        log.info("Goal saved: {}", goal.getId());
    }

    @Transactional
    public void updateFromProducer(GoalDTO goalDTO) {
        log.info("Updating goal from producer: {}");

        try {
            UUID goalId = goalDTO.getId();
            UUID familyId = goalDTO.getFamilyId();

            if (goalId == null || familyId == null) {
                log.warn("Missing familyId or id in update event: {}", goalDTO);
                return;
            }

            Optional<Goals> existingOpt = repository.findByFamilyIdAndId(familyId, goalId);
            if (existingOpt.isEmpty()) {
                log.warn("Goal not found for update. Family: {}, Goal: {}", familyId, goalId);
                return;
            }

            Goals existing = existingOpt.get();
            existing.setName(goalDTO.getName());
            existing.setDescription(goalDTO.getDescription());
            existing.setFamilyId(familyId);
            existing.setCategoryId(goalDTO.getCategoryId());
            existing.setSavingsCap(goalDTO.getSavingsCap());
            existing.setDeadline(goalDTO.getDeadline());
            existing.setDailyGoal(goalDTO.getDailyGoal());

            repository.save(existing);
            log.info("Goal updated successfully: {} for family {}", goalId, familyId);

        } catch (Exception e) {
            log.error("Error processing Goal UPDATE event: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteFromProducer(Map<String, String> data) {
        try {
            String familyStr = data.get("familyId");
            String goalStr = data.get("goalId");

            if (familyStr == null || goalStr == null) {
                log.warn("Missing fields in DELETE event: {}", data);
                return;
            }

            UUID familyId = UUID.fromString(familyStr);
            UUID goalId = UUID.fromString(goalStr);

            if (repository.existsByFamilyIdAndId(familyId, goalId)) {
                repository.deleteByFamilyIdAndId(familyId, goalId);
                log.info("Goal deleted successfully: {} for family {}", goalId, familyId);
            } else {
                log.warn("Goal with id {} not found for family {}", goalId, familyId);
            }

        } catch (Exception e) {
            log.error("Error deleting goal from producer event: {}", e.getMessage(), e);
        }
    }
}