package io.github.caliburno.academy_accounting.controller.web;

import io.github.caliburno.academy_accounting.dto.MonthlyReportDTO;
import io.github.caliburno.academy_accounting.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public String monthlyRevenue(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {

        BigDecimal revenue = reportService.getTotalRevenueForMonth(year, month);

        model.addAttribute("revenue", revenue);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);
        return "report/monthly-report";
    }
}
