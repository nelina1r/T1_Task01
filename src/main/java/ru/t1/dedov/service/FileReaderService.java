package ru.t1.dedov.service;

import ru.t1.dedov.exceptions.DataFormatException;
import ru.t1.dedov.exceptions.NegativeSalaryException;
import ru.t1.dedov.model.Department;
import ru.t1.dedov.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReaderService {


    public static List<Department> readFromFileToList(String fileName) {
        List<Department> departmentList;
        Map<String, Department> departmentMap = new HashMap<>();
        int lineCounter = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                lineCounter++;
                try {
                    String[] specs = line.split(";");
                    if (specs.length != 3)
                        throw new DataFormatException();
                    String departmentName = specs[1].trim();
                    if (!departmentMap.containsKey(departmentName))
                        departmentMap.put(departmentName, new Department(departmentName));
                    departmentMap.get(departmentName).addEmployee(createEmployee(specs));
                } catch (NegativeSalaryException | NumberFormatException b) {
                    System.out.println("Invalid salary value in line : " + lineCounter);
                } catch (DataFormatException e) {
                    System.out.println("Invalid dataset in line : " + lineCounter);
                }
                //
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("File reading error: " + fileName);
            return null;
        }
        departmentList = new ArrayList<>(departmentMap.values());
        getDepartmentList(departmentList);
        return departmentList;
    }

    private static Employee createEmployee(String[] specs) throws NegativeSalaryException{
        BigDecimal salary = new BigDecimal((specs[2].trim()));
        if(salary.compareTo(BigDecimal.ZERO) < 0)
            throw new NegativeSalaryException();
        return new Employee(specs[0], specs[1], salary);
    }

    public static void getDepartmentList(List<Department> departmentList){
        for(Department d : departmentList){
            System.out.println("\nAverage salary in department " +
                               d.getName() + ": " +
                               d.getAverageSalary() +
                               "\nWith employees: ");
            d.getEmployeeList().forEach(x -> System.out.println(x.getName() + " " + x.getSalary()));
        }
    }
}
