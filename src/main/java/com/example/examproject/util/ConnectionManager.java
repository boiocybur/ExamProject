package com.example.examproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;

    private ConnectionManager() {
    }

    public static synchronized Connection getConnection(String dbUrl, String dbUsername, String dbPassword) {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            } catch (SQLException e) {
                System.out.println("Couldn't connect to db");
                e.printStackTrace();
            }
        }
        return conn;
    }
}
