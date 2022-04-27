package ru.t1.dedov.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterService {

    public static void writeInFile(List<String> list, String fileName){
        try(FileWriter fileWriter = new FileWriter(fileName)){
            for(String line : list){
                fileWriter.write(line + "\n");
            }
        }catch (IOException e){
            System.out.println("Error with output file");
        }
    }
}
