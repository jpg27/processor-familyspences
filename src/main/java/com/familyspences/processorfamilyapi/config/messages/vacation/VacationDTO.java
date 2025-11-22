package com.familyspences.processorfamilyapi.config.messages.vacation;

import java.time.LocalDate;
import java.util.UUID;

public class VacationDTO {

    private UUID id;
    private UUID familyId;
    private String tituloVacation;
    private String descripcionVacation;
    private LocalDate fechaInicioVacation;
    private LocalDate fechaFinVacation;
    private String lugarVacation;
    private Double presupuestoVacation;

    // Constructor vacío
    public VacationDTO() {}

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFamilyId() {
        return familyId;
    }

    public void setFamilyId(UUID familyId) {
        this.familyId = familyId;
    }

    public String getTituloVacation() {
        return tituloVacation;
    }

    public void setTituloVacation(String tituloVacation) {
        this.tituloVacation = tituloVacation;
    }

    public String getDescripcionVacation() {
        return descripcionVacation;
    }

    public void setDescripcionVacation(String descripcionVacation) {
        this.descripcionVacation = descripcionVacation;
    }

    public LocalDate getFechaInicioVacation() {
        return fechaInicioVacation;
    }

    public void setFechaInicioVacation(LocalDate fechaInicioVacation) {
        this.fechaInicioVacation = fechaInicioVacation;
    }

    public LocalDate getFechaFinVacation() {
        return fechaFinVacation;
    }

    public void setFechaFinVacation(LocalDate fechaFinVacation) {
        this.fechaFinVacation = fechaFinVacation;
    }

    public String getLugarVacation() {
        return lugarVacation;
    }

    public void setLugarVacation(String lugarVacation) {
        this.lugarVacation = lugarVacation;
    }

    public Double getPresupuestoVacation() {
        return presupuestoVacation;
    }

    public void setPresupuestoVacation(Double presupuestoVacation) {
        this.presupuestoVacation = presupuestoVacation;
    }
}
