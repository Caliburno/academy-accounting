package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.model.Exam;
import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import io.github.caliburno.academy_accounting.repository.ExamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private AcademicYearService academicYearService;

    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    public Optional<Exam> findById(Long id) {
        return examRepository.findById(id);
    }

    public List<Exam> findByAcademicYear(AcademicYear academicYear) {
        return examRepository.findByAcademicYear(academicYear);
    }

    public List<Exam> findByLevel(CourseLevel courseLevel) {
        return examRepository.findByLevel(courseLevel);
    }

    public List<Exam> findUpcomingExams() {
        return examRepository.findByExamDateAfter(LocalDate.now());
    }

    public Exam save(Exam exam) {
        if (exam.getAcademicYear() == null) {
            exam.setAcademicYear(academicYearService.getActiveYear());
        }
        return examRepository.save(exam);
    }

    public void deleteById(Long id) {
        examRepository.deleteById(id);
    }
}
