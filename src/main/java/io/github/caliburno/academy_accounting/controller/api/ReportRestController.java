package io.github.caliburno.academy_accounting.controller.api;

import io.github.caliburno.academy_accounting.dto.PaymentDTO;
import io.github.caliburno.academy_accounting.model.Course;
import io.github.caliburno.academy_accounting.model.Enrollment;
import io.github.caliburno.academy_accounting.model.MonthlyPayment;
import io.github.caliburno.academy_accounting.model.Student;
import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import io.github.caliburno.academy_accounting.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class ReportRestController {

    private final ReportService reportService;

    @GetMapping("/revenue/{year}/{month}")
    public ResponseEntity<BigDecimal> getRevenueForMonth(
            @PathVariable Integer year,
            @PathVariable Integer month) {
        BigDecimal revenue = reportService.getTotalRevenueForMonth(year, month);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/total-outstanding")
    public ResponseEntity<BigDecimal> getTotalOutstanding() {
        BigDecimal total = reportService.getTotalOutstanding();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/overdue-payments")
    public ResponseEntity<List<PaymentDTO>> getOverduePayments() {
        List<MonthlyPayment> payments = reportService.getOverduePayments();
        List<PaymentDTO> dtos = payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private PaymentDTO convertToDTO( MonthlyPayment payment) {
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
                .isOverdue(payment.getStatus() == PaymentStatus.OVERDUE)
                .build();
    }

    private String getMonthName(int month) {
        String[] months = {"", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months[month];
    }
}
