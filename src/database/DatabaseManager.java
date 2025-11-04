package database;

import java.io.*;
import java.util.*;
import java.sql.*;

public class DatabaseManager {
    private static final Properties props = new Properties();
    private static final String URL = "jdbc:postgresql://localhost:5432/quesaco2";
    private static final String USER = "jonathangodwin";
    private static final String PASSWORD = "1detorixXx";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
