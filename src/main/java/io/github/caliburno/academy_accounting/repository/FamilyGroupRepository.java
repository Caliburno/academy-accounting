package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.FamilyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long> {

    Optional<FamilyGroup> findByName(String name);
}
