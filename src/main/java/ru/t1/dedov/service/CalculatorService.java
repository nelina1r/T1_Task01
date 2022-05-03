package ru.t1.dedov.service;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.model.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculatorService {

    public static List<String> calculateEmployeeTransfers(Map<String, Department> departmentMap){
        List<String> outputLinesList = new ArrayList<>();
        int transferCounter = 0;
        for(Department departmentFrom : departmentMap.values()){
            for(Department departmentTo : departmentMap.values()){
                if(departmentFrom.equals(departmentTo))
                    continue;
                for(Employee employee : departmentFrom.getEmployeeList()){
                    if(employee.getSalary().compareTo(departmentFrom.getAverageSalary()) < 0
                            && employee.getSalary().compareTo(departmentTo.getAverageSalary()) > 0) {
                        transferCounter++;
                        outputLinesList.add(outputLineFormatter(departmentFrom, departmentTo, employee, transferCounter));
                    }
                }
            }
        }
        return outputLinesList;
    }

    public static String outputLineFormatter(Department departmentFrom, Department departmentTo, Employee employee, int transferCounter){
        String outputLine = "Transfer №" + transferCounter + ": " + employee.getName() + " from " + departmentFrom.getName() +
                " to " + departmentTo.getName() + "\n" +
                "Average salary in " + departmentFrom.getName() + " before transfer: " + departmentFrom.getAverageSalary() + "\n" +
                "After transfer: " +
                (departmentFrom.getSummaryEmployeesSalary().subtract(employee.getSalary())).divide(BigDecimal.valueOf(departmentFrom.getEmployeeList().size() - 1), 2,  RoundingMode.HALF_UP) + "\n" +
                "Average salary in " + departmentTo.getName() + " before transfer: " + departmentTo.getAverageSalary() + "\n" +
                "After transfer: " +
                (departmentTo.getSummaryEmployeesSalary().add(employee.getSalary())).divide(BigDecimal.valueOf(departmentTo.getEmployeeList().size() + 1), 2,  RoundingMode.HALF_UP) + "\n";
        System.out.println(outputLine);
        return outputLine;
    }
}
