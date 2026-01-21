package io.github.caliburno.academy_accounting.dto;

import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course name is required")
    @Size(max = 100, message = "Course name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Course level is required")
    private CourseLevel level;

    @NotNull(message = "Course price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal basePrice;

    private Long academicYearId;
    private Integer academicYear;
}
