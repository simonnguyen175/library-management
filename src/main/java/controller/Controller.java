package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Controller {
    public static Connection connection;

    public void initialize() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUser = "root";
        String dbPassword = "0";

        try {
            Thread dbThread = new Thread(() -> {
                try {
                    connection = DriverManager.getConnection(url, dbUser, dbPassword);
                    System.out.println("Database connection successful");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Database connection failed");
                }
            });
            dbThread.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error starting database connection thread");
        }
    }
}