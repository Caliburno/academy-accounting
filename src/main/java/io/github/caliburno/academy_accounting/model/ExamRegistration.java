package io.github.caliburno.academy_accounting.model;

import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
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
@Table(name = "exam_registrations")
public class ExamRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "amount_paid", nullable = false)
    private BigDecimal amountPaid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "examRegistration", cascade = CascadeType.ALL)
    private List<ExamPayment> examPayment;
}
