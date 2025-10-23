package com.familyspences.processorfamilyapi.domain.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "task")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID familyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private UUID idResponsible;

    @Column(name = "vacation_id")
    private UUID  idVacations;

    @Column(name = "expenseve_id")
    private UUID idExpenseve;


    public Tasks() { // Noncompliant - method is empty
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public UUID getIdResponsible() {
        return idResponsible;
    }

    public void setIdResponsible(final UUID idResponsible) {
        this.idResponsible = idResponsible;
    }

    public void setIdVacations(final UUID  idVacations) {
        this.idVacations = idVacations;
    }
    public UUID  getIdVacations() {
        return idVacations;
    }
    public UUID  getIdExpenseve() {
        return idExpenseve;
    }

    public void setIdExpenseve(final UUID  idExpenseve) {
        this.idExpenseve = idExpenseve;
    }

    public UUID getFamilyId() {
        return familyId;
    }

    public void setFamilyId(UUID familyId) {
        this.familyId = familyId;
    }
}




