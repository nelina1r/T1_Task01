package ru.t1.dedov.controller;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.service.CalculatorService;
import ru.t1.dedov.service.FileReaderService;
import ru.t1.dedov.service.FileWriterService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println("Invalid CLI arguments \n" +
                               "Expected: 'input_file_path output_file_path'");
            return;
        }
        try {
            Map<String, Department> departmentMap = FileReaderService.readFromFileToList(args[0]);
            FileReaderService.printDepartmentList(new ArrayList<>(departmentMap.values()));
            CalculatorService.calculateAllPossibleEmployeeTransfers(departmentMap);
            FileWriterService.writeInFile(CalculatorService.outputLines, args[1]);
        }catch (IOException e){
            System.out.println("File reading error: " + args[0]);
        }
    }
}

