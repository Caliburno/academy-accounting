package io.github.caliburno.academy_accounting.dto;

import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ExamDTO {
    private Long id;

    @NotNull(message = "Exam name is required")
    @Size(max = 100, message = "Exam name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Course level is required")
    private CourseLevel level;

    @NotNull(message = "Exam price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    private LocalDate examDate;

    private Long academicYearId;
    private Integer academicYear;
    private Integer registrationCount;

}
