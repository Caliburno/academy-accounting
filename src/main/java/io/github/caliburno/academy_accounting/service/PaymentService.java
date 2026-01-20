package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.MonthlyPayment;
import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import io.github.caliburno.academy_accounting.repository.MonthlyPaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private MonthlyPaymentRepository monthlyPaymentRepository;

    public List<MonthlyPayment> findAll() {
        return monthlyPaymentRepository.findAll();
    }

    public Optional<MonthlyPayment> findById(Long id) {
        return monthlyPaymentRepository.findById(id);
    }

    public MonthlyPayment markAsPaid(Long paymentId) {
        MonthlyPayment payment = findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDate.now());

        return monthlyPaymentRepository.save(payment);
    }

    public List<MonthlyPayment> findOutstandingPayments() {
        return monthlyPaymentRepository.findByStatusIn(
                Arrays.asList(PaymentStatus.PENDING, PaymentStatus.OVERDUE)
        );
    }

    public void markOverduePayments() {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        List<MonthlyPayment> allPending = monthlyPaymentRepository.findByStatus(PaymentStatus.PENDING);

        allPending.forEach(payment -> {

            if (payment.getYear() < currentYear) {
                payment.setStatus(PaymentStatus.OVERDUE);
                monthlyPaymentRepository.save(payment);
            } else if (payment.getYear().equals(currentYear) && payment.getMonth() < currentMonth) {
                payment.setStatus(PaymentStatus.OVERDUE);
                monthlyPaymentRepository.save(payment);
            }
        });
    }

    public List<MonthlyPayment> findByYearAndMonth(Integer year, Integer month) {
        return monthlyPaymentRepository.findByYearAndMonth(year, month);
    }
}
