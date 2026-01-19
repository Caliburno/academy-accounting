package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.model.Course;
import io.github.caliburno.academy_accounting.model.Enrollment;
import io.github.caliburno.academy_accounting.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent(Student student);

    List<Enrollment> findByStudentAndAcademicYear(Student student, AcademicYear year);

    List<Enrollment> findByCourse(Course course);

    List<Enrollment> findByAcademicYear_ActiveTrue();
}
