package com.familyspences.processorfamilyapi.repository.task;

import com.familyspences.processorfamilyapi.domain.task.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Tasks, UUID> {
    boolean existsByFamilyIdAndId(UUID familyId, UUID id);
    void deleteByFamilyIdAndId(UUID familyId, UUID id);
    java.util.Optional<Tasks> findByFamilyIdAndId(UUID familyId, UUID id);
}

