package com.familyspences.processorfamilyapi.repository.family;

import com.familyspences.processorfamilyapi.domain.family.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {
    Optional<Family> findByFamilyName(String familyName);
    boolean existsByFamilyName(String familyName);
}
