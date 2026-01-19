package io.github.caliburno.academy_accounting.model;

import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseLevel level;

    @Column(name = "price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "date", nullable = false)
    private LocalDate examDate;

    @ManyToOne
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear year;

    @OneToMany(mappedBy = "exam")
    private List<ExamRegistration> registrationList;

}
