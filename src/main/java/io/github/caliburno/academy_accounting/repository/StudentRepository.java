package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
