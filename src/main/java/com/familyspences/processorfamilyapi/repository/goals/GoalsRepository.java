package com.familyspences.processorfamilyapi.repository.goals;

import com.familyspences.processorfamilyapi.domain.goals.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, UUID> {

    Optional<Goals> findByFamilyIdAndId(UUID familyId, UUID id);
    boolean existsByFamilyIdAndId(UUID familyId, UUID id);
    void deleteByFamilyIdAndId(UUID familyId, UUID id);
}