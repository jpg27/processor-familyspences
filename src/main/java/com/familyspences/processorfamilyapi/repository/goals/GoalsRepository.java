package com.familyspences.processorfamilyapi.repository.goals;

import com.familyspences.processorfamilyapi.domain.goals.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, UUID> {
    Optional<Goals> findByCategoryIdAndId(UUID categoryId, UUID id);
    boolean existsByCategoryIdAndId(UUID categoryId, UUID id);
    void deleteByCategoryIdAndId(UUID categoryId, UUID id);
}