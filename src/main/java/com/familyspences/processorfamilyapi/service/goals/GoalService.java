package com.familyspences.processorfamilyapi.service.goals;

import com.familyspences.processorfamilyapi.domain.goals.Goals;
import com.familyspences.processorfamilyapi.repository.goals.GoalsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoalService {

    private final GoalsRepository repository;

    public GoalService(GoalsRepository repository) {
        this.repository = repository;
    }

    public List<Goals> getAllGoals() {
        return repository.findAll();
    }

    public Optional<Goals> getGoalById(UUID id) {
        return repository.findById(id);
    }

    public Goals createGoal(Goals goal) {
        return repository.save(goal);
    }

    public Optional<Goals> updateGoal(UUID id, Goals goalDetails) {
        return repository.findById(id).map(goal -> {
            goal.setName(goalDetails.getName());
            goal.setDescription(goalDetails.getDescription());
            goal.setSavingsCap(goalDetails.getSavingsCap());
            goal.setDeadline(goalDetails.getDeadline());
            goal.setDailyGoal(goalDetails.getDailyGoal());
            goal.setCategoryId(goalDetails.getCategoryId());
            return repository.save(goal);
        });
    }

    public boolean deleteGoal(UUID id) {
        return repository.findById(id).map(goal -> {
            repository.delete(goal);
            return true;
        }).orElse(false);
    }
}