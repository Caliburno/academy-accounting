package io.github.caliburno.academy_accounting.dto;

import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentDTO {
    private Long id;

    @NotNull(message = "Student id is required")
    private Long studentId;
    private String studentName;

    @NotNull(message = "Course id ir required")
    private Long courseId;
    private String courseName;
    private CourseLevel courseLevel;

    private Long academicYearId;
    private Integer academicYear;

    private BigDecimal finalPrice;
    private BigDecimal discountApplied;
    private LocalDate enrollmentDate;

    private Integer totalPayments;
    private Integer paidPayments;
    private BigDecimal amountPaid;
    private BigDecimal amountPending;

}
