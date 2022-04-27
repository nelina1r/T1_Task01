package ru.t1.dedov.controller;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.service.CalculatorService;
import ru.t1.dedov.service.FileReaderService;
import ru.t1.dedov.service.FileWriterService;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<Department> departmentList = new ArrayList<>();
        try{
            departmentList = FileReaderService.readFromFileToList(args[0]);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("File name not entered");
        }
        if(departmentList != null){
            try{
                FileWriterService.writeInFile(CalculatorService.calculateEmployeeTransfers(departmentList), args[1]);
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("\nOutput file name not entered");
            }
        }
    }
}
