package io.github.caliburno.academy_accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyReportDTO {
    private Integer year;
    private Integer month;
    private String monthName;
    private BigDecimal totalRevenue;
    private Integer paymentCount;

}
