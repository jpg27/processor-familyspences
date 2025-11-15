package com.familyspences.processorfamilyapi.config.messages.task;

import java.time.LocalDate;
import java.util.UUID;

public class TaskDTO {
    private UUID id;
    private UUID familyId;
    private String name;
    private String description;
    private boolean status;
    private LocalDate creationDate;
    private UUID idResponsible;
    private UUID idExpenseve;
    private UUID idVacations;

    // Constructor vacío
    public TaskDTO() {}

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getFamilyId() { return familyId; }
    public void setFamilyId(UUID familyId) { this.familyId = familyId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public UUID getIdResponsible() { return idResponsible; }
    public void setIdResponsible(UUID idResponsible) { this.idResponsible = idResponsible; }

    public UUID getIdExpenseve() { return idExpenseve; }
    public void setIdExpenseve(UUID idExpenseve) { this.idExpenseve = idExpenseve; }

    public UUID getIdVacations() { return idVacations; }
    public void setIdVacations(UUID idVacations) { this.idVacations = idVacations; }
}