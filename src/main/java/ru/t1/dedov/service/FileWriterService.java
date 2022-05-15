package ru.t1.dedov.service;

import java.io.*;

public class FileWriterService {

    public static String OUTPUT_FILE_NAME;

    public static void writeLineInFile(String text){
        File file = new File(OUTPUT_FILE_NAME);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file,true);
            fr.write(text);

        } catch (IOException e) {
            System.out.println("Error occurred during writing in file: " + OUTPUT_FILE_NAME);
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                System.out.println("Error occurred during closing the file: " + OUTPUT_FILE_NAME);
            }
        }
    }
}
