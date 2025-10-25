package com.familyspences.processorfamilyapi.service.pet;

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
    public void saveFromProducer(Pet pet) {
        log.info("Saving pet from producer: {}", pet);
        repository.save(pet);
    }

    @Transactional
    public void updateFromProducer(Pet updatedPet) {
        try {
            UUID petId = updatedPet.getId();
            UUID familyId = updatedPet.getFamilyId();

            if (petId == null || familyId == null) {
                log.warn("Missing familyId or id in update event: {}", updatedPet);
                return;
            }

            Optional<Pet> existingOpt = repository.findByFamilyIdAndId(familyId, petId);
            if (existingOpt.isEmpty()) {
                log.warn("Pet not found for update. Family: {}, Pet: {}", familyId, petId);
                return;
            }

            Pet existing = existingOpt.get();
            existing.setFullName(updatedPet.getFullName());
            existing.setPetType(updatedPet.getPetType());
            existing.setBreed(updatedPet.getBreed());
            existing.setBirthDate(updatedPet.getBirthDate());

            repository.save(existing);
            log.info("Pet updated successfully: {} for family {}", petId, familyId);

        } catch (Exception e) {
            log.error("Error processing Pet UPDATE event: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteFromProducer(Map<String, String> data) {
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