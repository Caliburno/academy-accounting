package io.github.caliburno.academy_accounting.controller.web;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.service.AcademicYearService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/academic-years")
@RequiredArgsConstructor
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    @GetMapping
    public String listAcademicYears(Model model) {
        List<AcademicYear> academicYears = academicYearService.findAll();
        model.addAttribute("academicYears", academicYears);
        return "academic-year/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("academicYear", new AcademicYear());
        return "academic-year/form";
    }

    @PostMapping
    public String createAcademicYear(
            @Valid @ModelAttribute("academicYear") AcademicYear academicYear,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            return "academic-year/form";
        }

        try {
            academicYearService.save(academicYear);
            redirectAttributes.addFlashAttribute("success", "Academic Year created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/academic-years";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        AcademicYear academicYear = academicYearService.findById(id)
                .orElseThrow(() -> new RuntimeException("Academic Year not found"));
        model.addAttribute("academicYear", academicYear);
        return "academic-year/form";
    }

    @PostMapping("/{id}")
    public String updateAcademicYear(
            @PathVariable Long id,
            @Valid @ModelAttribute("academicYear") AcademicYear academicYear,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            return "academic-year/form";
        }

        academicYear.setId(id);
        try {
            academicYearService.save(academicYear);
            redirectAttributes.addFlashAttribute("success", "Academic Year updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/academic-years";
    }

    @GetMapping("/{id}/delete")
    public String deleteAcademicYear(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            academicYearService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Academic Year deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete active academic year!");
        }
        return "redirect:/academic-years";
    }

    @PostMapping("/{id}/activate")
    public String activateAcademicYear(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            AcademicYear academicYear = academicYearService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Academic Year not found"));
            academicYear.setActive(true);
            academicYearService.save(academicYear);
            redirectAttributes.addFlashAttribute("success", "Academic Year activated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/academic-years";
    }
}