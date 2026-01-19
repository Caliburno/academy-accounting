package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
