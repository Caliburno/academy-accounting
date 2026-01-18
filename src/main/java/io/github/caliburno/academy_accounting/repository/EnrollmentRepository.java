package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
