package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.service.HRMService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class HrmApplication {

    private final EmployeeRepository repository = new EmployeeRepository();
    private final HRMService hrmService = new HRMService(repository);
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new HrmApplication().run();
    }

    private void run() {
        while (true) {
            System.out.println("\n=== HRM System Menu ===");
            System.out.println("1. Show all employees");
            System.out.println("2. Add employee");
            System.out.println("3. Delete employee by ID");
            System.out.println("4. Find employee by ID");
            System.out.println("5. Find employee by position");
            System.out.println("6. Show statistics");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> showAllEmployees();
                case 2 -> addEmployee();
                case 3 -> deleteEmployee();
                case 4 -> findEmployeeById();
                case 5 -> findEmployeeByPosition();
                case 6 -> showStatistics();
                case 7 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Unknown option.");
            }
        }
    }

    private void findEmployeeByPosition() {
        System.out.print("Enter employee position: ");
        String position = scanner.nextLine();

        List<Employee> employees = hrmService.getEmployeesByPosition(position);

        if (employees.isEmpty()) {
            System.out.println("Employee not found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private void showAllEmployees() {
        List<Employee> employees = hrmService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private void addEmployee() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter position: ");
            String position = scanner.nextLine();

            double salary;
            while (true) {
                System.out.print("Enter salary: ");
                try {
                    salary = Double.parseDouble(scanner.nextLine());
                    if (salary < 0) {
                        System.out.println("Salary cannot be negative.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid salary format.");
                }
            }

            LocalDate hireDate;
            while (true) {
                System.out.print("Enter hire date (yyyy-mm-dd): ");
                try {
                    hireDate = LocalDate.parse(scanner.nextLine());
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format.");
                }
            }

            Employee employee = hrmService.addEmployee(name, position, salary, hireDate);
            System.out.println("Employee added successfully with ID: " + employee.getId());
        } catch (Exception e) {
            System.out.println("Error while adding employee: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        try {
            System.out.print("Enter employee ID to delete: ");
            Long id = Long.parseLong(scanner.nextLine());
            boolean deleted = hrmService.deleteEmployee(id);
            System.out.println(deleted ? "Employee deleted." : "Employee not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void findEmployeeById() {
        try {
            System.out.print("Enter employee ID: ");
            Long id = Long.parseLong(scanner.nextLine());
            Employee employee = hrmService.getEmployeeById(id);
            System.out.println(employee != null ? employee : "Employee not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void showStatistics() {
        double averageSalary = hrmService.calculateAverageSalary();
        Employee topPaidEmployee = hrmService.findTopPaidEmployee();

        System.out.println("Average salary: " + averageSalary);
        System.out.println(topPaidEmployee != null ? "Top paid employee: " + topPaidEmployee : "No employees in system.");
    }
}