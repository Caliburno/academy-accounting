package io.github.caliburno.academy_accounting.controller.web;

import io.github.caliburno.academy_accounting.model.Course;
import io.github.caliburno.academy_accounting.service.AcademicYearService;
import io.github.caliburno.academy_accounting.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final AcademicYearService academicYearService;

    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "course/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("academicYears", academicYearService.findAll());
        return "course/form";
    }

    @PostMapping
    public String createCourse(
            @Valid @ModelAttribute("course") Course course,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("academicYears", academicYearService.findAll());
            return "course/form";
        }

        courseService.save(course);
        redirectAttributes.addFlashAttribute("success", "Course created successfully!");
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id).orElseThrow(() -> new RuntimeException("No course with that id"));
        model.addAttribute("course", course);
        model.addAttribute("academicYears", academicYearService.findAll());
        return "course/form";
    }

    @PostMapping("/{id}")
    public String updateCourse(
            @PathVariable Long id,
            @Valid @ModelAttribute("course") Course course,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("academicYears", academicYearService.findAll());
            return "course/form";
        }

        course.setId(id);
        courseService.save(course);
        redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        return "redirect:/courses";
    }

    @GetMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete course with enrollments!");
        }
        return "redirect:/courses";
    }

}
