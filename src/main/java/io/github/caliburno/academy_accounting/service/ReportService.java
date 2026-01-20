package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.MonthlyPayment;
import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import io.github.caliburno.academy_accounting.repository.MonthlyPaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ReportService {

    @Autowired
    private MonthlyPaymentRepository monthlyPaymentRepository;

    public BigDecimal getTotalRevenueForMonth(Integer year, Integer month) {
        List<MonthlyPayment> paymentList = monthlyPaymentRepository.findByYearAndMonth(year, month);

        return paymentList
                .stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID)
                .map(MonthlyPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalOutstanding() {
        List<MonthlyPayment> outstanding = monthlyPaymentRepository.findByStatusIn(
                List.of(PaymentStatus.PENDING, PaymentStatus.OVERDUE)
        );

        return outstanding.stream()
                .map(MonthlyPayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MonthlyPayment> getOverduePayments() {
        return monthlyPaymentRepository.findByStatus(PaymentStatus.OVERDUE);
    }
}
