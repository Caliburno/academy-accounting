package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
}
