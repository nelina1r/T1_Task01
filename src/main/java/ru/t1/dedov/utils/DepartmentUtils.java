package ru.t1.dedov.utils;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.model.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DepartmentUtils {

    public static BigDecimal calculateAverageSalaryWithExtraEmployees(Department department, List<Employee> employeeList){
        BigDecimal averageSalary = department.getAverageSalary();
        for(Employee e : employeeList){
            averageSalary = averageSalary.add(e.getSalary());
        }
        return averageSalary.divide(new BigDecimal(department.getEmployeeList().size() + employeeList.size()), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateAverageSalaryWithoutSomeEmployees(Department department, List<Employee> employeeList){
        BigDecimal averageSalary = new BigDecimal(0);
        if (employeeList.size() == department.getEmployeeList().size())
            return averageSalary;
        for (Employee employee : department.getEmployeeList()) {
            if (!employeeList.contains(employee)) {
                averageSalary = averageSalary.add(employee.getSalary());
            }
        }
        return averageSalary.divide(new BigDecimal(department.getEmployeeList().size() - employeeList.size()),2, RoundingMode.HALF_UP);
    }
}
