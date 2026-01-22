package io.github.caliburno.academy_accounting.controller.api;

import io.github.caliburno.academy_accounting.dto.PaymentDTO;
import io.github.caliburno.academy_accounting.model.Course;
import io.github.caliburno.academy_accounting.model.Enrollment;
import io.github.caliburno.academy_accounting.model.MonthlyPayment;
import io.github.caliburno.academy_accounting.model.Student;
import io.github.caliburno.academy_accounting.service.EnrollmentService;
import io.github.caliburno.academy_accounting.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentRestController {

    private final PaymentService paymentService;
    private final EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<MonthlyPayment> payments = paymentService.findAll();
        List<PaymentDTO> dtos = payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        MonthlyPayment monthlyPayment = paymentService.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        return ResponseEntity.ok(convertToDTO(monthlyPayment));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStudent(@PathVariable Long id) {
        List<MonthlyPayment> payments = paymentService.findByStudentId(id);
        List<PaymentDTO> dtos = payments
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PaymentDTO>> getPendingPayments() {
        List<MonthlyPayment> payment = paymentService.findOutstandingPayments();
        List<PaymentDTO> dtos = payment.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<PaymentDTO>> getOverduePayments() {
        List<MonthlyPayment> payments = paymentService.findOverduePayments();
        List<PaymentDTO> dtos = payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{id}/mark-paid")
    public ResponseEntity<PaymentDTO> markAsPaid(@PathVariable Long id) {
        MonthlyPayment payment = paymentService.markAsPaid(id);
        return ResponseEntity.ok(convertToDTO(payment));
    }

    private PaymentDTO convertToDTO(MonthlyPayment payment) {
        Enrollment enrollment = payment.getEnrollment();
        Student student = enrollment.getStudent();
        Course course = enrollment.getCourse();

        return PaymentDTO.builder()
                .id(payment.getId())
                .enrollmentId(enrollment.getId())
                .studentId(student.getId())
                .studentName(student.getName())
                .courseId(course.getId())
                .courseName(course.getName())
                .year(payment.getYear())
                .month(payment.getMonth())
                .monthName(getMonthName(payment.getMonth()))
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .isOverdue(paymentService.isOverdue(payment))
                .build();
    }

    private String getMonthName(int month) {
        String[] months = {"", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months[month];
    }

}
