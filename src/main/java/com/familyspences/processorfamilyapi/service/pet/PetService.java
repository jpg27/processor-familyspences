package com.familyspences.processorfamilyapi.service.pet;

import com.familyspences.processorfamilyapi.config.messages.pets.petsDTO;
import com.familyspences.processorfamilyapi.domain.pet.Pet;
import com.familyspences.processorfamilyapi.repository.pet.IRepositoryPet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetService {

    private static final Logger log = LoggerFactory.getLogger(PetService.class);
    private final IRepositoryPet repository;

    public PetService(IRepositoryPet repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveFromProducer(petsDTO petDTO) {
        log.info("Guardando pet desde producer...");

        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setFamilyId(petDTO.getFamilyId());
        pet.setFullName(petDTO.getFullName());
        pet.setPetType(petDTO.getPetType());
        pet.setBreed(petDTO.getBreed());
        pet.setBirthDate(petDTO.getBirthDate());

        repository.save(pet);
        log.info(" Pet guardado: {}", pet.getId());
    }

    @Transactional
    public void updateFromProducer(petsDTO petDTO) {
        log.info("Actualizando pet desde producer...");
        try {
            UUID petId = petDTO.getId();
            UUID familyId = petDTO.getFamilyId();

            if (petId == null || familyId == null) {
                log.warn("Missing familyId or id in update event: {}", petDTO);
                return;
            }

            Optional<Pet> existingOpt = repository.findByFamilyIdAndId(familyId, petId);
            if (existingOpt.isEmpty()) {
                log.warn(" Pet not found for update. Family: {}, Pet: {}", familyId, petId);
                return;
            }

            Pet existing = existingOpt.get();
            existing.setFullName(petDTO.getFullName());
            existing.setPetType(petDTO.getPetType());
            existing.setBreed(petDTO.getBreed());
            existing.setBirthDate(petDTO.getBirthDate());

            repository.save(existing);
            log.info("Pet updated successfully: {} for family {}", petId, familyId);

        } catch (Exception e) {
            log.error("Error processing Pet UPDATE event: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteFromProducer(Map<String, String> data) {
        log.info("Eliminando pet desde producer...");
        try {
            String familyStr = data.get("familyId");
            String petStr = data.get("petId");

            if (familyStr == null || petStr == null) {
                log.warn("Missing fields in DELETE event: {}", data);
                return;
            }

            UUID familyId = UUID.fromString(familyStr);
            UUID petId = UUID.fromString(petStr);

            if (repository.existsByFamilyIdAndId(familyId, petId)) {
                repository.deleteByFamilyIdAndId(familyId, petId);
                log.info("Pet deleted successfully: {} for family {}", petId, familyId);
            } else {
                log.warn("Pet with id {} not found for family {}", petId, familyId);
            }

        } catch (Exception e) {
            log.error("Error deleting pet from producer event: {}", e.getMessage(), e);
        }
    }
}