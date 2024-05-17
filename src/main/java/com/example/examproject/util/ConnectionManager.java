package com.example.examproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;
    private ConnectionManager() {
    }
    public static Connection getConnection(String dbUrl, String dbUserName, String dbPassword) {
        if (conn != null) return conn;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to db");
            e.printStackTrace();
        }
        return conn;
    }
}
