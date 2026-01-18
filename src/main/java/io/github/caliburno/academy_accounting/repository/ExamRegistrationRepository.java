package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration, Long> {
}
