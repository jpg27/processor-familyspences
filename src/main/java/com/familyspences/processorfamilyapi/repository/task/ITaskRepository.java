package com.familyspences.processorfamilyapi.repository.task;

import com.familyspences.processorfamilyapi.domain.task.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Tasks, UUID> {

    List<Tasks> findByFamilyId(UUID familyId);
    Optional<Tasks> findByFamilyIdAndId(UUID familyId, UUID id);

}

