package io.github.caliburno.academy_accounting.controller.web;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.model.Course;
import io.github.caliburno.academy_accounting.model.Enrollment;
import io.github.caliburno.academy_accounting.model.Student;
import io.github.caliburno.academy_accounting.service.AcademicYearService;
import io.github.caliburno.academy_accounting.service.CourseService;
import io.github.caliburno.academy_accounting.service.EnrollmentService;
import io.github.caliburno.academy_accounting.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final AcademicYearService academicYearService;

    @GetMapping
    public String listEnrollments(Model model) {
        List<Enrollment> enrollments = enrollmentService.findAll();
        model.addAttribute("enrollments", enrollments);
        return "enrollment/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findActiveYearCourse());
        return "enrollment/form";
    }

    @PostMapping
    public String createEnrollment(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            RedirectAttributes redirectAttributes) {

        Student student = studentService.findById(studentId)
                .orElseThrow(() -> new RuntimeException("No student with such id"));
        Course course = courseService.findById(courseId)
                .orElseThrow(() -> new RuntimeException("No course with such id"));

        AcademicYear academicYear = academicYearService.getActiveYear();

        Enrollment enrollment = enrollmentService.createEnrollment(student, course, academicYear);

        redirectAttributes.addFlashAttribute("success",
                "Enrollment created! Final price: $" + enrollment.getFinalPrice());
        return "redirect:/enrollments";
    }

    @GetMapping("/{id}/delete")
    public String deleteEnrollment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        enrollmentService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Enrollment deleted successfully!");
        return "redirect:/enrollments";
    }
}
