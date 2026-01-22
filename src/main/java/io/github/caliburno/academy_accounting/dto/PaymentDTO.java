package io.github.caliburno.academy_accounting.dto;

import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class PaymentDTO {
    private Long id;

    private Long enrollmentId;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseName;

    private Integer year;
    private Integer month;
    private String monthName;

    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.01", message = "Payment must be greater than 0")
    private BigDecimal amount;

    private PaymentStatus status;
    private LocalDate paymentDate;
    private Boolean isOverdue;

}
