package ru.t1.dedov.service;

import ru.t1.dedov.model.Department;
import ru.t1.dedov.model.Employee;

import java.sql.*;
import java.util.Map;

public class DatabaseWriterService {

    private final String DATABASE_URL = "jdbc:postgresql://localhost:5432/task1_db";
    private final String USER = "postgres";
    private final String PASSWORD = "postgres";
    private final String DATABASE_DRIVER = "org.postgresql.Driver";

    public void insertData(Map<String, Department> departmentMap) throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Class.forName(DATABASE_DRIVER);
        connection.setAutoCommit(false);
        //
        String sql1 = "insert into department (name) values (?)";
        String sql2 = "insert into employee (name, salary, department_id) values (?,?,?)";
        //
        String[] generatedColumns = {"id"};
        PreparedStatement ps1 = connection.prepareStatement(sql1, generatedColumns);
        PreparedStatement ps2 = connection.prepareStatement(sql2);
        Long id = 0L;
        for (Map.Entry<String, Department> entry : departmentMap.entrySet()) {
            try {
                ps1.setString(1, entry.getKey());
                ps1.executeUpdate();
                //
                ResultSet rs = ps1.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
                //
                for (Employee emp : entry.getValue().getEmployeeList()) {
                    ps2.setString(1, emp.getName());
                    ps2.setBigDecimal(2, emp.getSalary());
                    ps2.setLong(3, id);
                    ps2.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        }
    }
}
