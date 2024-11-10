package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Controller {
    protected Connection connection;

    public void initialize() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUser = "root";
        String dbPassword = "123";

        try {
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            System.out.println("Database connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection failed");
        }
    }
}