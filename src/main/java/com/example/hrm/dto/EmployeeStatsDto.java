package com.example.hrm.dto;

import java.math.BigDecimal;

public class EmployeeStatsDto {
    private BigDecimal averageSalary;
    private String topPaidEmployeeName;
    private BigDecimal topPaidEmployeeSalary;

    public EmployeeStatsDto() {}

    public EmployeeStatsDto(BigDecimal averageSalary, String topPaidEmployeeName, BigDecimal topPaidEmployeeSalary) {
        this.averageSalary = averageSalary;
        this.topPaidEmployeeName = topPaidEmployeeName;
        this.topPaidEmployeeSalary = topPaidEmployeeSalary;
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }
    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public String getTopPaidEmployeeName() {
        return topPaidEmployeeName;
    }
    public void setTopPaidEmployeeName(String topPaidEmployeeName) {
        this.topPaidEmployeeName = topPaidEmployeeName;
    }

    public BigDecimal getTopPaidEmployeeSalary() {
        return topPaidEmployeeSalary;
    }
    public void setTopPaidEmployeeSalary(BigDecimal topPaidEmployeeSalary) {
        this.topPaidEmployeeSalary = topPaidEmployeeSalary;
    }
}