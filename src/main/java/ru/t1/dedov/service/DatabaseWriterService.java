package ru.t1.dedov.service;

import ru.t1.dedov.model.Department;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseWriterService {

    private final String JDBC_DRIVER = "org.postgresql.Driver";
    private final String DATABASE_URL = "jdbc:postgresql://localhost:5432/task1_db";
    private final String USER = "postgres";
    private final String PASSWORD = "postgres";

    public void insertData(Map<String, Department> departmentMap) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }
}
