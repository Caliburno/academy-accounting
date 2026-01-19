package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.model.Course;
import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByAcademicYear(AcademicYear academicYear);

    List<Course> findByAcademicYear_ActiveTrue();

    List<Course> findByLevel(CourseLevel level);
}
