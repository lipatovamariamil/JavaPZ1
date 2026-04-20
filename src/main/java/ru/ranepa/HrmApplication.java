package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.service.HRMService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class HrmApplication {
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        HRMService hrmService = new HRMService(repository);
        Scanner scanner = new Scanner(System.in);

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
                case 1:
                    showAllEmployees(hrmService);
                    break;
                case 2:
                    addEmployee(scanner, hrmService);
                    break;
                case 3:
                    deleteEmployee(scanner, hrmService);
                    break;
                case 4:
                    findEmployeeById(scanner, hrmService);
                    break;
                case 5:
                    findEmployeeByPosition(scanner, hrmService);
                    break;
                case 6:
                    showStatistics(hrmService);
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

            }

        }

    }

    private static void findEmployeeByPosition(Scanner scanner, HRMService hrmService) {
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

    private static void showAllEmployees(HRMService hrmService) {
        List<Employee> employees = hrmService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private static void addEmployee(Scanner scanner, HRMService hrmService) {
        try {
            // имя
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            // позиция
            System.out.print("Enter position: ");
            String position = scanner.nextLine();
            // зп с проверкой на число и неотрицательность
            double salary;
            while (true) {
                System.out.print("Enter salary: ");
                try {
                    salary = Double.parseDouble(scanner.nextLine());
                    if (salary < 0) {
                        System.out.println("Salary cannot be negative.");
                        continue;
                    }
                    break; // всё ок — выходим из цикла
                } catch (NumberFormatException e) {
                    System.out.println("Invalid salary format.");
                }
            }
            // дата найма с проверкой формата
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
            // добавляем сотрудника
            Employee employee = hrmService.addEmployee(name, position, salary, hireDate);
            System.out.println("Employee added successfully with ID: " + employee.getId());
        } catch (Exception e) {
            System.out.println("Error while adding employee: " + e.getMessage());

        }

    }

    private static void deleteEmployee(Scanner scanner, HRMService hrmService) {
        try {
            System.out.print("Enter employee ID to delete: ");
            Long id = Long.parseLong(scanner.nextLine());
            boolean deleted = hrmService.deleteEmployee(id);
            if (deleted) {
                System.out.println("Employee deleted.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void findEmployeeById(Scanner scanner, HRMService hrmService) {
        try {
            System.out.print("Enter employee ID: ");
            Long id = Long.parseLong(scanner.nextLine());
            Employee employee = hrmService.getEmployeeById(id);
            if (employee != null) {
                System.out.println(employee);
            } else {
                System.out.println("Employee not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void showStatistics(HRMService hrmService) {
        double averageSalary = hrmService.calculateAverageSalary();
        Employee topPaidEmployee = hrmService.findTopPaidEmployee();
        System.out.println("Average salary: " + averageSalary);
        if (topPaidEmployee != null) {
            System.out.println("Top paid employee: " + topPaidEmployee);
        } else {
            System.out.println("No employees in system.");
        }
    }
}