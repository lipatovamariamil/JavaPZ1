package com.example.hrm.service;
import com.example.hrm.model.Employee;
import com.example.hrm.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> getEmployeeByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }

    public List<Employee> getEmployeeByHireDate(LocalDate hireDate) {
        return employeeRepository.findByHireDate(hireDate);
    }

    public List<Employee> getEmployeeByCreatedDate(LocalDate createdDate) {
        return employeeRepository.findByCreatedDate(createdDate);
    }

    public List<Employee> getEmployeeBySalaryGreaterThanEqual(BigDecimal salary) {
        return employeeRepository.findBySalaryGreaterThanEqual(salary);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    public double calculateAverageSalary() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Employee employee : employees) {
            sum += employee.getSalary().doubleValue();
        }
        return sum / employees.size();
    }

    public Employee findTopPaidEmployee() {
        return employeeRepository.findAll().stream()
                .max((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()))
                .orElse(null);
    }
}