package com.example.hrm.controller;

import com.example.hrm.model.Employee;
import com.example.hrm.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    //GET     /api/employees           — все сотрудники
    @GetMapping("/api/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    //GET     /api/employees/{id}      — сотрудник по ID
    @GetMapping("/api/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //GET     /api/employees/position/{position}  — по должности
    @GetMapping("/api/employees/position/{position}")
    public List<Employee> getEmployeeByPosition(@PathVariable String position) {
        return employeeService.getEmployeeByPosition(position);
    }

    //GET     /api/employees/stats     — статистика (средняя ЗП, топ)
    @GetMapping("/api/employees/stats")
    public ResponseEntity<String> getEmployeeStats() {
        double averageSalary = employeeService.calculateAverageSalary();
        Employee topPaidEmployee = employeeService.findTopPaidEmployee();
        String stats = String.format("Average Salary: %.2f, Top Paid Employee: %s",
                averageSalary, topPaidEmployee != null ? topPaidEmployee.getName() : "N/A");
        return ResponseEntity.ok(stats);
    }

    //POST    /api/employees           — создать сотрудника
    @PostMapping("/api/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    //DELETE  /api/employees/{id}      — удалить сотрудника
    @DeleteMapping("/api/employees/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
}