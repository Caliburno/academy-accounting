package io.github.caliburno.academy_accounting.dto;

import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutstandingPaymentDTO {
    private Long paymentId;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseName;
    private Integer year;
    private Integer month;
    private String monthName;
    private BigDecimal amount;
    private PaymentStatus status;
    private Integer daysOverdue;
    private LocalDate dueDate;

}
