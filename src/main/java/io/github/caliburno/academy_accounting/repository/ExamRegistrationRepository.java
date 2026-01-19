package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.ExamRegistration;
import io.github.caliburno.academy_accounting.model.Student;
import io.github.caliburno.academy_accounting.model.Exam;
import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration, Long> {

    List<ExamRegistration> findByStudent(Student student);

    List<ExamRegistration> findByExam(Exam exam);

    List<ExamRegistration> findByPaymentStatus(PaymentStatus status);

    Optional<ExamRegistration> findByStudentAndExam(Student student, Exam exam);
}
