package io.github.caliburno.academy_accounting.controller.web;

import io.github.caliburno.academy_accounting.service.CourseService;
import io.github.caliburno.academy_accounting.service.EnrollmentService;
import io.github.caliburno.academy_accounting.service.PaymentService;
import io.github.caliburno.academy_accounting.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final PaymentService paymentService;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("totalStudents", studentService.findAll().size());
        model.addAttribute("totalCourses", courseService.findAll().size());
        model.addAttribute("totalEnrollments", enrollmentService.findAll().size());
        model.addAttribute("pendingPayments", paymentService.findPendingPayments().size());
        model.addAttribute("overduePayments", paymentService.findOverduePayments().size());

        return "home";
    }
}
