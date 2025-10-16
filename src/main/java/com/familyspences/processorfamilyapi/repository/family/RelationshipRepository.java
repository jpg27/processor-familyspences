package com.familyspences.processorfamilyapi.repository.family;

import com.familyspences.processorfamilyapi.domain.family.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, UUID> {
    Optional<Relationship> findByType(String type);
    boolean existsByType(String type);
}
