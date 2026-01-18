package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.MonthlyPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyPaymentRepository extends JpaRepository<MonthlyPayment, Long> {
}
