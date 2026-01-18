package io.github.caliburno.academy_accounting.repository;

import io.github.caliburno.academy_accounting.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
