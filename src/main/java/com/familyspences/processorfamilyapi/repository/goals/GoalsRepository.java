package com.familyspences.processorfamilyapi.repository.goals;

import com.familyspences.processorfamilyapi.domain.goals.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, UUID> {
}
