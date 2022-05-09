package ru.t1.dedov.service;

import ru.t1.dedov.exceptions.DataFormatException;
import ru.t1.dedov.exceptions.InvalidSalaryException;
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
                    if(isValueBlankCheck(specs, lineCounter)){
                        line = bufferedReader.readLine();
                        continue;
                    }
                    dataSetCheck(specs, lineCounter);
                    String departmentName = specs[1].trim();
                    salaryCheck(specs[2].trim(), lineCounter);
                    if (!departmentMap.containsKey(departmentName))
                        departmentMap.put(departmentName, new Department(departmentName));
                    departmentMap.get(departmentName).addEmployee(createEmployee(specs));
                } catch (InvalidSalaryException | NumberFormatException | DataFormatException b) {
                    System.out.println(b.getMessage());
                }
                line = bufferedReader.readLine();
            }
            return departmentMap;
        }
    }

    private static boolean isValueBlankCheck(String[] specs, int lineCounter){
        if(Arrays.stream(specs).anyMatch(x -> (x.isBlank()))){
            System.out.println("In " + lineCounter + " line one or more values contains only spaces or empty");
            return true;
        }
        return false;
    }

    private static void dataSetCheck(String[] specs, int lineCounter) throws DataFormatException {
        if (specs.length != 3)
            throw new DataFormatException("Invalid dataset in line: " + lineCounter);
    }

    private static void salaryCheck(String s, int lineCounter) throws InvalidSalaryException {
        BigDecimal salary = new BigDecimal((s.trim()));
        if(salary.compareTo(BigDecimal.ZERO) < 0 | salary.scale() > 2)
            throw new InvalidSalaryException("Invalid salary value in line : " + lineCounter);
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
