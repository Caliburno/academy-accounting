package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.ExamPayment;
import io.github.caliburno.academy_accounting.model.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExamPaymentRepository extends JpaRepository<ExamPayment, Long> {

    List<ExamPayment> findByExamRegistration(ExamRegistration registration);

    List<ExamPayment> findByPaymentDateBetween(LocalDate start, LocalDate end);
}
