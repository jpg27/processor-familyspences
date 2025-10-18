package com.familyspences.processorfamilyapi.controller;

import com.familyspences.processorfamilyapi.dto.goals.GoalMessageDTO;
import com.familyspences.processorfamilyapi.producer.GoalsProducer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalsController {

    private final GoalsProducer goalsProducer;

    public GoalsController(GoalsProducer goalsProducer) {
        this.goalsProducer = goalsProducer;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createGoal(@Valid @RequestBody CreateGoalRequest request) {
        GoalMessageDTO.GoalData data = new GoalMessageDTO.GoalData();
        data.setName(request.getName());
        data.setDescription(request.getDescription());
        data.setCategoryId(request.getCategoryId());
        data.setSavingsCap(request.getSavingsCap());
        data.setDeadline(request.getDeadline());
        data.setDailyGoal(request.getDailyGoal());

        goalsProducer.sendGoalMessage("CREATE", data);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Solicitud de creación enviada al procesador");
        response.put("datos", data);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateGoal(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateGoalRequest request) {

        GoalMessageDTO.GoalData data = new GoalMessageDTO.GoalData();
        data.setId(id);
        data.setName(request.getName());
        data.setDescription(request.getDescription());
        data.setCategoryId(request.getCategoryId());
        data.setSavingsCap(request.getSavingsCap());
        data.setDeadline(request.getDeadline());
        data.setDailyGoal(request.getDailyGoal());

        goalsProducer.sendGoalMessage("UPDATE", data);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Solicitud de actualización enviada al procesador");
        response.put("id", id);
        response.put("datos", data);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteGoal(@PathVariable UUID id) {
        GoalMessageDTO.GoalData data = new GoalMessageDTO.GoalData();
        data.setId(id);

        goalsProducer.sendGoalMessage("DELETE", data);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Solicitud de eliminación enviada al procesador");
        response.put("id", id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getGoalById(@PathVariable UUID id) {
        GoalMessageDTO.GoalData data = new GoalMessageDTO.GoalData();
        data.setId(id);

        goalsProducer.sendGoalMessage("GET", data);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Solicitud de consulta enviada al procesador");
        response.put("id", id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllGoals() {
        GoalMessageDTO.GoalData data = new GoalMessageDTO.GoalData();

        goalsProducer.sendGoalMessage("GET", data);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Solicitud de consulta de todas las metas enviada al procesador");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public static class CreateGoalRequest {
        private String name;
        private String description;
        private UUID categoryId;
        private Double savingsCap;
        private LocalDateTime deadline;
        private Double dailyGoal;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public UUID getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(UUID categoryId) {
            this.categoryId = categoryId;
        }

        public Double getSavingsCap() {
            return savingsCap;
        }

        public void setSavingsCap(Double savingsCap) {
            this.savingsCap = savingsCap;
        }

        public LocalDateTime getDeadline() {
            return deadline;
        }

        public void setDeadline(LocalDateTime deadline) {
            this.deadline = deadline;
        }

        public Double getDailyGoal() {
            return dailyGoal;
        }

        public void setDailyGoal(Double dailyGoal) {
            this.dailyGoal = dailyGoal;
        }
    }

    public static class UpdateGoalRequest {
        private String name;
        private String description;
        private UUID categoryId;
        private Double savingsCap;
        private LocalDateTime deadline;
        private Double dailyGoal;

        // Getters y Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public UUID getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(UUID categoryId) {
            this.categoryId = categoryId;
        }

        public Double getSavingsCap() {
            return savingsCap;
        }

        public void setSavingsCap(Double savingsCap) {
            this.savingsCap = savingsCap;
        }

        public LocalDateTime getDeadline() {
            return deadline;
        }

        public void setDeadline(LocalDateTime deadline) {
            this.deadline = deadline;
        }

        public Double getDailyGoal() {
            return dailyGoal;
        }

        public void setDailyGoal(Double dailyGoal) {
            this.dailyGoal = dailyGoal;
        }
    }
}