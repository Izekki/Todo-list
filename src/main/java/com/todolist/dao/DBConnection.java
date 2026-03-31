package com.todolist.dao;

import com.todolist.config.AppConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(
                        AppConfig.DB_URL,
                        AppConfig.DB_USER,
                        AppConfig.DB_PASS
                );
            } catch (SQLException e) {
                System.err.println("Error conectando a la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
}