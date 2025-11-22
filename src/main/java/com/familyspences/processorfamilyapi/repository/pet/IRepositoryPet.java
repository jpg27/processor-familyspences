package com.familyspences.processorfamilyapi.repository.pet;

import com.familyspences.processorfamilyapi.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface IRepositoryPet extends JpaRepository<Pet, UUID> {
    boolean existsByFamilyIdAndId(UUID familyId, UUID id);
    void deleteByFamilyIdAndId(UUID familyId, UUID id);
    java.util.Optional<Pet> findByFamilyIdAndId(UUID familyId, UUID id);
}