package io.github.caliburno.academy_accounting.controller.web;

import io.github.caliburno.academy_accounting.dto.*;
import io.github.caliburno.academy_accounting.model.*;
import io.github.caliburno.academy_accounting.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;
    private final StudentService studentService;
    private final AcademicYearService academicYearService;

    @GetMapping
    public String listExams(Model model) {
        List<Exam> exams = examService.findAll();
        model.addAttribute("exams", exams);
        return "exam/list";
    }

    @GetMapping("/{id}/registration")
    public String showRegistrationForm(@PathVariable Long id, Model model) {
        Exam exam = examService.findById(id).orElseThrow(() -> new RuntimeException("No such exam exist"));
        List<Student> students = studentService.findAll();

        model.addAttribute("exam", exam);
        model.addAttribute("students", students);
        return "exam/registration";
    }

    @PostMapping("/{examId}/register")
    public String registerStudent(
            @PathVariable Long examId,
            @RequestParam Long studentId,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("success", "Student registered for exam!");
        return "redirect:/exams";
    }
}

