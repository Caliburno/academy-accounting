package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.Enrollment;
import io.github.caliburno.academy_accounting.model.MonthlyPayment;
import io.github.caliburno.academy_accounting.model.Student;
import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyPaymentRepository extends JpaRepository<MonthlyPayment, Long> {

    List<MonthlyPayment> findByEnrollment(Enrollment enrollment);

    List<MonthlyPayment> findByStatus(PaymentStatus status);

    List<MonthlyPayment> findByYearAndMonth(Integer year, Integer month);

    List<MonthlyPayment> findByStatusIn(List<PaymentStatus> statuses);

    List<MonthlyPayment> findByEnrollment_Student(Student student);
}
