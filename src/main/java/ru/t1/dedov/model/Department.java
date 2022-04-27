package ru.t1.dedov.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {

    private String name;

    private List<Employee> employeeList = new ArrayList<>();

    public Department(String name){
        this.name = name;
    }

    public void addEmployee(Employee employee){
        employeeList.add(employee);
    }

    public BigDecimal getAverageSalary() {
        BigDecimal averageSalary = new BigDecimal(0);
        for(Employee e : employeeList)
            averageSalary = averageSalary.add(e.getSalary());
        return averageSalary.divide(new BigDecimal(employeeList.size()), 2, RoundingMode.HALF_UP);
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
