package ru.t1.dedov.service;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.model.Employee;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculatorService {

    public static void calculateAllPossibleEmployeeTransfers(Map<String, Department> departmentMap) throws IOException {
        departmentMap.keySet();
        for(Department departmentFrom : departmentMap.values()){
            for(Department departmentTo : departmentMap.values()){
                if(!departmentFrom.equals(departmentTo)){
                    calculateTransfersInTwoDeps(departmentFrom, departmentTo);
                }
            }
        }
    }

    public static void calculateTransfersInTwoDeps(Department departmentFrom, Department departmentTo) {
        boolean exitFlag = true;
        for(Employee employee : departmentFrom.getEmployeeList()){
            if(employee.getSalary().compareTo(departmentTo.getAverageSalary()) > 0)
                exitFlag = false;
        }
        if(exitFlag) return;
        recursionSearching(departmentFrom, departmentTo, new LinkedList<>(), 0, departmentFrom.getEmployeeList().size());
    }

    public static void recursionSearching(Department depFrom, Department depTo, List<Employee> employeeList, int recursionStep, int recursionDeep) {
        if(!(recursionStep == recursionDeep)){
            Employee employee = depFrom.getEmployeeList().get(recursionStep);
            employeeList.add(employee);
            recursionSearching(depFrom, depTo, employeeList, recursionStep + 1, recursionDeep);
            employeeList.remove(employee);
            recursionSearching(depFrom, depTo, employeeList, recursionStep + 1, recursionDeep);
        } else {
            BigDecimal updatedDepFromAvgSalary = depFrom.calculateAverageSalaryWithoutSomeEmployees(employeeList);
            BigDecimal updatedDepToAvgSalary = depTo.calculateAverageSalaryWithExtraEmployees(employeeList);
            if(updatedDepToAvgSalary.compareTo(depTo.getAverageSalary()) > 0 &&
                    updatedDepFromAvgSalary.compareTo(depFrom.getAverageSalary()) > 0)
                FileWriterService.writeLineInFile(outputLineFormatter(depFrom, depTo, employeeList, updatedDepFromAvgSalary, updatedDepToAvgSalary));
        }
    }

    @Deprecated(since = "08.05.2022")
    public static List<String> calculateEmployeeTransfers(Map<String, Department> departmentMap){
        List<String> outputLinesList = new ArrayList<>();
        int transferCounter = 0;
        for(Department departmentFrom : departmentMap.values()){
            for(Department departmentTo : departmentMap.values()){
                if(departmentFrom.equals(departmentTo))
                    continue;
                if(departmentFrom.getAverageSalary().compareTo(departmentTo.getAverageSalary()) > 0) {
                    List<Employee> employeeList = departmentFrom.getEmployeeList()
                            .stream()
                            .filter(e -> (e.getSalary().compareTo(departmentFrom.getAverageSalary()) < 0 &&
                                          e.getSalary().compareTo(departmentTo.getAverageSalary()) > 0))
                            .collect(Collectors.toList());
                    for (Employee employee : employeeList) {
                        transferCounter++;
                        outputLinesList.add(outputLineFormatter(departmentFrom, departmentTo, employee, transferCounter));
                    }
                }
            }
        }
        return outputLinesList;
    }

    public static String outputLineFormatter(Department departmentFrom, Department departmentTo, Employee employee, int transferCounter){
         return "Transfer №" + transferCounter + ": " + employee.getName() + " from " + departmentFrom.getName() +
                " to " + departmentTo.getName() + "\n" +
                "Average salary in " + departmentFrom.getName() + " before transfer: " + departmentFrom.getAverageSalary() + "\n" +
                "After transfer: " +
                (departmentFrom.getSummaryEmployeesSalary().subtract(employee.getSalary())).divide(BigDecimal.valueOf(departmentFrom.getEmployeeList().size() - 1), 2,  RoundingMode.HALF_UP) + "\n" +
                "Average salary in " + departmentTo.getName() + " before transfer: " + departmentTo.getAverageSalary() + "\n" +
                "After transfer: " +
                (departmentTo.getSummaryEmployeesSalary().add(employee.getSalary())).divide(BigDecimal.valueOf(departmentTo.getEmployeeList().size() + 1), 2,  RoundingMode.HALF_UP) + "\n";
    }

    public static String outputLineFormatter(Department departmentFrom, Department departmentTo, List<Employee> employeeList, BigDecimal updatedDepFromAvgSalary, BigDecimal updatedDepToAvgSalary){
        String s1 =  "Transfer from " + departmentFrom.getName() + " to " + departmentTo.getName() + "\n" +
                    "Average salary in " + departmentFrom.getName() + " before transfer: " + departmentFrom.getAverageSalary() + "\n" +
                    "After transfer : " + updatedDepFromAvgSalary + "\n" +
                    "Average salary in " + departmentTo.getName() + " before transfer: " + departmentTo.getAverageSalary() + "\n" +
                    "After transfer : " + updatedDepToAvgSalary + "\n" +
                    "Employee list: " + "\n";
        StringBuilder sb = new StringBuilder();
        for(Employee e: employeeList){
            sb.append(e.getName()).append("\n");
        }
        return s1 + sb + "\n";
    }
}
