package io.github.caliburno.academy_accounting.controller.web;

import io.github.caliburno.academy_accounting.dto.*;
import io.github.caliburno.academy_accounting.model.*;
import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import io.github.caliburno.academy_accounting.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("exam", new Exam());
        model.addAttribute("acdemicYears", academicYearService.findAll());
        return "exam/form";
    }

    @PostMapping(value = {"", "/{id}"})
    public String createExam(
            @PathVariable(required = false) Long id,
            @RequestParam String name,
            @RequestParam String level,
            @RequestParam BigDecimal totalPrice,
            @RequestParam String examDate,
            @RequestParam(required = false) Long academicYearId,
            RedirectAttributes redirectAttributes) {
        Exam exam = new Exam();

        if (id != null) {
            exam = examService.findById(id).orElseThrow(() -> new RuntimeException("Exam not found"));
        } else {
            exam = new Exam();
        }
        exam.setName(name);
        exam.setLevel(CourseLevel.valueOf(level));
        exam.setTotalPrice(totalPrice);
        exam.setExamDate(LocalDate.parse(examDate));

        if (academicYearId != null) {
            exam.setAcademicYear(academicYearService.findById(academicYearId).orElseThrow(() -> new RuntimeException("Academic Year not found")));
        } else {
            exam.setAcademicYear(academicYearService.getActiveYear());
        }

        examService.save(exam);
        redirectAttributes.addFlashAttribute("success", "Exam created successfully!");
        return "redirect:/exams";
    }

    @GetMapping("/{id}/delete")
    public String deleteExam(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            examService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Exam deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete exam with registrations!");
        }
        return "redirect:/exams";
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

