import model.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {

    private static final String FILE_PATH = "C:\\Users\\dedov\\OneDrive\\Рабочий стол\\input.txt";  //изменить

    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>();
        Set<String> departmentSet = new HashSet<>();
        try {
            File file = new File(FILE_PATH);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] specs = line.split(" ");
                employeeList.add(new Employee(specs[0], specs[1], Double.parseDouble(specs[2])));
                departmentSet.add(specs[1]);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String d : departmentSet){
            Double averageSalary = 0D;
            int counter = 0;
            for(Employee e : employeeList){
                if(d.equals(e.getDepartment())){
                    averageSalary += e.getSalary();
                    counter++;
                    System.out.println(e.getName() + " " + e.getDepartment() + " " + e.getSalary() + "\n");
                }
            }
            System.out.println("Average salary for " + d + ": " + Math.round(averageSalary/counter) + "\n");
        }
    }
}
