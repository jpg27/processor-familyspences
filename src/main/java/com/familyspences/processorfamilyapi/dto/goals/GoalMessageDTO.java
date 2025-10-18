package com.familyspences.processorfamilyapi.dto.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;

public class GoalMessageDTO {

    @JsonProperty("operacion")
    private String operation;

    @JsonProperty("datos")
    private GoalData data;

    public GoalMessageDTO() {}

    public GoalMessageDTO(String operation, GoalData data) {
        this.operation = operation;
        this.data = data;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public GoalData getData() {
        return data;
    }

    public void setData(GoalData data) {
        this.data = data;
    }

    public static class GoalData {
        private UUID id;
        private String name;
        private String description;

        @JsonProperty("category_id")
        private UUID categoryId;

        @JsonProperty("savings_cap")
        private Double savingsCap;

        private LocalDateTime deadline;

        @JsonProperty("daily_goal")
        private Double dailyGoal;

        public GoalData() {}

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

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

