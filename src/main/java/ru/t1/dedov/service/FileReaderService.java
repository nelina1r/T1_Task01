package ru.t1.dedov.service;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class FileReaderService {

    private static final int MAX_EMPLOYEE_LINE_LENGTH = 50;

    public static Map<String, Department> readFromFileToList(String fileName) throws IOException{
        Map<String, Department> departmentMap = new HashMap<>();
        int lineCounter = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                lineCounter++;
                try {
                    String[] specs = line.split(";");
                    if (checkDataConsistency(specs, lineCounter)) {
                        line = bufferedReader.readLine();
                        continue;
                    }
                    String departmentName = specs[1].trim();
                    departmentMap.putIfAbsent(departmentName, new Department(departmentName, UUID.randomUUID()));
                    departmentMap.get(departmentName).addEmployee(createEmployee(specs));
                } catch (NumberFormatException b) {
                    System.out.println("Salary not a number in line: " + lineCounter);
                }
                line = bufferedReader.readLine();
            }
        }
        return departmentMap;
    }

    private static boolean checkDataConsistency(String[] specs, int lineCounter){
        if(isValueBlankCheck(specs)){
            System.out.println("In " + lineCounter + " line one or more values contains only spaces or empty");
            return true;
        }
        if(dataSetCheck(specs)) {
            System.out.println("Invalid dataset in line: " + lineCounter);
            return true;
        }
        if(isSalaryDigitCheck(specs[2])) {
            return false;
        }
        if(salaryAdequacyCheck(specs[2].trim())) {
            System.out.println("Invalid salary value in line : " + lineCounter);
            return true;
        }
        return false;
    }

    private static boolean isValueBlankCheck(String[] specs){
        return Arrays.stream(specs).anyMatch(x -> (x.isBlank()));
    }

    private static boolean dataSetCheck(String[] specs) {
        return specs.length != 3;
    }

    private static boolean isSalaryDigitCheck(String s){
        try{
            Double.parseDouble(s);
        }catch (NumberFormatException e){
            return true;
        }
        return false;
    }

    private static boolean salaryAdequacyCheck(String s) {
        BigDecimal salary = new BigDecimal((s.trim()));
        return salary.compareTo(BigDecimal.ZERO) < 0 || salary.scale() > 2;
    }

    private static Employee createEmployee(String[] specs){
        BigDecimal salary = new BigDecimal((specs[2].trim()));
        return new Employee(specs[0], salary);
    }

    public static void printDepartmentList(List<Department> departmentList){
        DecimalFormat df = new DecimalFormat("#,###.00");
        for(Department d: departmentList){
            System.out.println("\nAverage salary in department " +
                               d.getName() + ": " +
                               df.format(d.getAverageSalary()) +
                               "\nWith employees: ");
            for(Employee e: d.getEmployeeList()) {
                String sb = " ".repeat(Math.max(0, (MAX_EMPLOYEE_LINE_LENGTH - e.getSalary().toString().length() - e.getName().length())));
                System.out.println(e.getName() + sb + df.format(e.getSalary()));
            }
        }
    }
}
