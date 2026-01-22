package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.model.Course;
import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import io.github.caliburno.academy_accounting.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AcademicYearService academicYearService;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> findActiveYearCourse() {
        return courseRepository.findByAcademicYear_ActiveTrue();
    }

    public List<Course> findByAcademicYear(AcademicYear year) {
        return courseRepository.findByAcademicYear(year);
    }

    public List<Course> findByLevel(CourseLevel level) {
        return courseRepository.findByLevel(level);
    }

    public Course save(Course course) {
        if (course.getAcademicYear() == null) {
            course.setAcademicYear(academicYearService.getActiveYear());
        }
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        Course course = findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        if (course.getEnrollment() != null && !course.getEnrollment().isEmpty()) {
            throw new RuntimeException("You can't delete a course with enrolled students");
        }

        courseRepository.deleteById(id);
    }

}
