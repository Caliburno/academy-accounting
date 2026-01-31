package io.github.caliburno.academy_accounting.controller.web;

import io.github.caliburno.academy_accounting.model.*;
import io.github.caliburno.academy_accounting.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final FamilyGroupService familyGroupService;

    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "student/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("familyGroups", familyGroupService.findAll());
        return "student/form";
    }

    @PostMapping
    public String createStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult result,
            @RequestParam(required = false) Long familyGroupId,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("familyGroups", familyGroupService.findAll());
            return "student/form";
        }

        if (familyGroupId != null) {
            FamilyGroup familyGroup = familyGroupService.findById(familyGroupId)
                    .orElseThrow(() -> new RuntimeException("Family group not found"));
            student.setFamilyGroup(familyGroup);
        }

        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Student created successfully!");
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        model.addAttribute("familyGroups", familyGroupService.findAll());
        return "student/form";
    }

    @PostMapping("/{id}")
    public String updateStudent(
            @PathVariable Long id,
            @Valid @ModelAttribute("student") Student student,
            BindingResult result,
            @RequestParam(required = false) Long familyGroupId,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("familyGroups", familyGroupService.findAll());
            return "student/form";
        }

        student.setId(id);

        if (familyGroupId != null) {
            FamilyGroup familyGroup = familyGroupService.findById(familyGroupId)
                    .orElseThrow(() -> new RuntimeException("Family group not found"));
            student.setFamilyGroup(familyGroup);
        }

        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        return "redirect:/students";
    }
}