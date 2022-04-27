package ru.t1.dedov.service;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class CalculatorService {

    public static List<String> calculateEmployeeTransfers(List<Department> departmentList){
        List<String> outputLinesList = new ArrayList<>();
        for(Department departmentFrom : departmentList){
            for(Department departmentTo : departmentList){
                if(departmentFrom.equals(departmentTo))
                    continue;
                for(Employee employee : departmentFrom.getEmployeeList()){
                    if(employee.getSalary().compareTo(departmentFrom.getAverageSalary()) < 0
                            && employee.getSalary().compareTo(departmentTo.getAverageSalary()) > 0) {
                        System.out.println("Transfer : " + employee.getName() + " from : " + departmentFrom.getName() +
                                " to " + departmentTo.getName());
                        outputLinesList.add("Transfer : " + employee.getName() + " from : " + departmentFrom.getName() +
                                " to " + departmentTo.getName());
                    }
                }
            }
        }
        return outputLinesList;
    }
}
