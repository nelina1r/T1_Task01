package ru.t1.dedov.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {

    private String name;

    private List<Employee> employeeList;

    public Department(String name){
        this.name = name;
        employeeList = new ArrayList<>();
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
