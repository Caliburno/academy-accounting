package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.MonthlyPayment;
import io.github.caliburno.academy_accounting.model.Student;
import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import io.github.caliburno.academy_accounting.repository.MonthlyPaymentRepository;
import io.github.caliburno.academy_accounting.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private MonthlyPaymentRepository monthlyPaymentRepository;

    private StudentRepository studentRepository;

    public List<MonthlyPayment> findAll() {
        return monthlyPaymentRepository.findAll();
    }

    public Optional<MonthlyPayment> findById(Long id) {
        return monthlyPaymentRepository.findById(id);
    }

    public List<MonthlyPayment> findByStudentId(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        return monthlyPaymentRepository.findByEnrollment_Student(student);
    }

    public MonthlyPayment markAsPaid(Long paymentId) {
        MonthlyPayment payment = findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDate.now());

        return monthlyPaymentRepository.save(payment);
    }

    public List<MonthlyPayment> findOutstandingPayments() {
        return monthlyPaymentRepository.findByStatusIn(
                Arrays.asList(PaymentStatus.PENDING, PaymentStatus.OVERDUE)
        );
    }

    public List<MonthlyPayment> findOverduePayments() {
        return monthlyPaymentRepository.findByStatus(PaymentStatus.OVERDUE);
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

    public Boolean isOverdue(MonthlyPayment payment) {
        if (payment.getStatus() == PaymentStatus.PAID) return false;

        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        if (payment.getYear() < currentYear) return true;

        return payment.getYear() == currentYear && payment.getMonth() < currentMonth;
    }
}
