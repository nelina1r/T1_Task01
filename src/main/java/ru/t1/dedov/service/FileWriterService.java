package ru.t1.dedov.service;

import java.io.*;

public class FileWriterService {

    public static String OUTPUT_FILE_NAME;

    public static void writeLineInFile(String text) {
        try(FileWriter fileWriter = new FileWriter(OUTPUT_FILE_NAME, true)){
            fileWriter.write(text);
        }catch (IOException e){
            System.out.println("Error occurred during writing the file: " + OUTPUT_FILE_NAME);
        }
    }
}
