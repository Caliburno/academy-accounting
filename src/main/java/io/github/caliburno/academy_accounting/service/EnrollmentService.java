package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.*;
import io.github.caliburno.academy_accounting.model.enums.PaymentStatus;
import io.github.caliburno.academy_accounting.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AcademicYearService academicYearService;

    private static final int START_MONTH = 3;
    private static final int END_MONTH = 12;

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> findByStudent(Student student) {
        return enrollmentRepository.findByStudent(student);
    }

    public List<Enrollment> findByCourse(Course course) {
        return enrollmentRepository.findByCourse(course);
    }

    public List<Enrollment> findActiveEnrollments() {
        return enrollmentRepository.findByAcademicYear_ActiveTrue();
    }

    public Enrollment createEnrollment(Student student, Course course, AcademicYear academicYear) {

        BigDecimal finalPrice = calculateFinalPrice(student, course);

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .academicYear(academicYear)
                .finalPrice(finalPrice)
                .enrollmentDate(LocalDate.now())
                .build();

        enrollment = enrollmentRepository.save(enrollment);

        List<MonthlyPayment> monthlyPayments = generateMontlyPayments(enrollment);
        enrollment.setMonthlyPayments(monthlyPayments);

        return enrollmentRepository.save(enrollment);
    }

    private BigDecimal calculateFinalPrice(Student student, Course course) {
        BigDecimal basePrice = course.getBasePrice();

        if(student.getFamilyGroup() != null) {
            FamilyGroup family = student.getFamilyGroup();

            if(family.getStudents() != null && family.getStudents().size() >= 2) {
                BigDecimal discount = family.getDiscountPercentage();
                BigDecimal discountAmount = basePrice
                        .multiply(discount)
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

                return basePrice.subtract(discountAmount);
            }
        }

        return basePrice;
    }

    private List<MonthlyPayment> generateMontlyPayments(Enrollment enrollment) {
        List<MonthlyPayment> payments = new ArrayList<>();
        BigDecimal monthlyAmount = enrollment.getFinalPrice();
        int year = enrollment.getAcademicYear().getYear();

        for (int month = START_MONTH; month <= END_MONTH; month++) {
            MonthlyPayment payment = MonthlyPayment.builder()
                    .enrollment(enrollment)
                    .year(year)
                    .month(month)
                    .amount(monthlyAmount)
                    .status(PaymentStatus.PENDING)
                    .build();

            payments.add(payment);
        }

        return payments;
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteById(Long id) {
        enrollmentRepository.deleteById(id);
    }

}
