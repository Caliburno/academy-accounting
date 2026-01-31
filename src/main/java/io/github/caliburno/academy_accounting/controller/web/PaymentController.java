package io.github.caliburno.academy_accounting.controller.web;
import io.github.caliburno.academy_accounting.dto.*;
import io.github.caliburno.academy_accounting.model.*;
import io.github.caliburno.academy_accounting.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ReportService reportService;

    @GetMapping
    public String listMonthlyPayments(Model model) {
        List<MonthlyPayment> payments = paymentService.findAll();
        model.addAttribute("payments", payments);
        return "payment/monthly";
    }

    @GetMapping("/outstanding")
    public String listOutstandingPayments(Model model) {
        List<MonthlyPayment> payments = paymentService.findOutstandingPayments();
        BigDecimal total = reportService.getTotalOutstanding();

        model.addAttribute("payments", payments);
        model.addAttribute("totalOutstanding", total);
        return "payment/outstanding";
    }

    @GetMapping("/pending")
    public String listPendingPayments(Model model) {
        List<MonthlyPayment> payments = paymentService.findPendingPayments();
        model.addAttribute("payments", payments);
        return "payment/monthly";
    }

    @GetMapping("/overdue")
    public String listOverduePayments(Model model) {
        List<MonthlyPayment> payments = paymentService.findOverduePayments();
        model.addAttribute("payments", payments);
        return "payment/monthly";
    }

    @PostMapping("/{id}/mark-paid")
    public String markAsPaid(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        paymentService.markAsPaid(id);
        redirectAttributes.addFlashAttribute("success", "Payment marked as paid!");
        return "redirect:/payments";
    }
}

