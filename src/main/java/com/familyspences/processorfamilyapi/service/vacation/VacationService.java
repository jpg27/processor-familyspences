package com.familyspences.processorfamilyapi.service.vacation;

import com.familyspences.processorfamilyapi.domain.vacation.Vacation;
import com.familyspences.processorfamilyapi.repository.vacation.VacationRepository;
import com.familyspences.processorfamilyapi.config.messages.vacation.VacationDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class VacationService {

    private final VacationRepository repository;

    public VacationService(VacationRepository repository) {
        this.repository = repository;
    }

    // ============ MÉTODO DEL CONSUMIDOR PARA CREAR =============
    public Vacation saveFromProducer(VacationDTO dto) {
        Vacation vacation = new Vacation();
        vacation.setTitulo(dto.getTituloVacation());
        vacation.setDescripcion(dto.getDescripcionVacation());
        vacation.setFechaInicio(dto.getFechaInicioVacation());
        vacation.setFechaFin(dto.getFechaFinVacation());
        vacation.setLugar(dto.getLugarVacation());

        if (dto.getPresupuestoVacation() != null) {
            vacation.setPresupuesto(BigDecimal.valueOf(dto.getPresupuestoVacation()));
        }

        return repository.save(vacation);
    }

    // ============ MÉTODO DEL CONSUMIDOR PARA ACTUALIZAR ============
    public Optional<Vacation> updateFromProducer(VacationDTO dto) {
        return repository.findById(dto.getId()).map(existing -> {

            existing.setTitulo(dto.getTituloVacation());
            existing.setDescripcion(dto.getDescripcionVacation());
            existing.setFechaInicio(dto.getFechaInicioVacation());
            existing.setFechaFin(dto.getFechaFinVacation());
            existing.setLugar(dto.getLugarVacation());

            if (dto.getPresupuestoVacation() != null) {
                existing.setPresupuesto(BigDecimal.valueOf(dto.getPresupuestoVacation()));
            }

            return repository.save(existing);
        });
    }

    // ============ MÉTODO DEL CONSUMIDOR PARA ELIMINAR ============
    public boolean deleteFromProducer(UUID id) {
        return repository.findById(id).map(existing -> {
            repository.delete(existing);
            return true;
        }).orElse(false);
    }

    // ============ MÉTODOS NORMALES DE CRUD (Controllers REST) ============
    public Optional<Vacation> getVacationById(UUID id) {
        return repository.findById(id);
    }
}
