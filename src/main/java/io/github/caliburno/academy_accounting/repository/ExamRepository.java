package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.Exam;
import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByAcademicYear(AcademicYear year);

    List<Exam> findByLevel(CourseLevel level);

    List<Exam> findByExamDateAfter(LocalDate date);
}
