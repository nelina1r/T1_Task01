package ru.t1.dedov.service;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.model.Employee;

import java.sql.*;
import java.util.Map;

public class DatabaseWriterService {

    private final String DATABASE_URL = "jdbc:postgresql://localhost:5432/task1_db";
    private final String USER = "postgres";
    private final String PASSWORD = "postgres";

    public void insertData(Map<String, Department> departmentMap) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Statement statement = null;

        Class.forName("org.postgresql.Driver");

        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        connection.setAutoCommit(false);
        statement = connection.createStatement();
        for(Map.Entry<String, Department> entry : departmentMap.entrySet()) {
            try {
                String SQL1 = "insert into department (name) values (" +  "'" + entry.getKey() + "'" + ")";
                statement.executeUpdate(SQL1);
                String SQL2 = "select id from department where name = '" + entry.getKey() + "'";
                ResultSet resultSet = statement.executeQuery(SQL2);
                Long id = 0L;
                while(resultSet.next())
                    id = resultSet.getLong("id");
                for(Employee emp : entry.getValue().getEmployeeList()){
                    String SQL3 =
                            "insert into employee (name, salary, department_id) values (" + "'" +emp.getName() + "'" + ", " + emp.getSalary() + "," + id + ")";
                    statement.executeUpdate(SQL3);
                }
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        }
    }
}
