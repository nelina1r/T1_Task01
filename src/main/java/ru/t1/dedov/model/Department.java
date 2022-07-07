package ru.t1.dedov.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Department {

    private String name;

    public List<Employee> employeeList;

    public Department(String name){
        this.name = name;
        employeeList = new ArrayList<>();
    }

    public BigDecimal calculateAverageSalaryWithExtraEmployees(List<Employee> employeeList){
        BigDecimal averageSalary = getAverageSalary();
        for(Employee e : employeeList){
            averageSalary = averageSalary.add(e.getSalary());
        }
        return averageSalary.divide(new BigDecimal(getEmployeeList().size() + employeeList.size()), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAverageSalaryWithoutSomeEmployees(List<Employee> employeeList){
        BigDecimal averageSalary = new BigDecimal(0);
        if (employeeList.size() == getEmployeeList().size())
            return averageSalary;
        for (Employee employee : getEmployeeList()) {
            if (!employeeList.contains(employee)) {
                averageSalary = averageSalary.add(employee.getSalary());
            }
        }
        return averageSalary.divide(new BigDecimal(getEmployeeList().size() - employeeList.size()),2, RoundingMode.HALF_UP);
    }

    public BigDecimal getSummaryEmployeesSalary(){
        if(employeeList.size() == 0)
            return BigDecimal.ZERO;
        return employeeList.stream().map(x -> x.getSalary()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void addEmployee(Employee employee){
        employeeList.add(employee);
    }

    public BigDecimal getAverageSalary() {
        if (employeeList.size() == 0)
            return BigDecimal.ZERO;
        return getSummaryEmployeesSalary().divide(new BigDecimal(employeeList.size()), 2, RoundingMode.HALF_UP);
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        employeeList.forEach(x -> stringBuilder.append(x.toString()).append("\r\n"));
        return stringBuilder.toString();
    }
}
