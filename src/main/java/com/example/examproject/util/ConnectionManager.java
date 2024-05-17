package com.example.examproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection connection;
    private ConnectionManager() {
    }

    public static Connection getConnection(String dbUrl, String dbUserName, String dbPassword) {

        if (connection != null) return connection;

        try {connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database.");
            e.printStackTrace();
        }
        return connection;
    }

}

