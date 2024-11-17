package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.StageManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController extends Controller{

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    public void initialize() {
        super.initialize();
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLoginButtonAction();
            }
        });

        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLoginButtonAction();
            }
        });
    }

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            errorMessage.setText("Login successful!");
            errorMessage.setTextFill(javafx.scene.paint.Color.BLUE);
            openDashboard();
        } else {
            errorMessage.setText("Invalid username or password.");
            errorMessage.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    private boolean authenticate(String username, String password) {
        try{
//            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, username);
//            statement.setString(2, password);
//
//            ResultSet resultSet = statement.executeQuery();
//            return resultSet.next();
            return true ;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openDashboard() {
        StageManager.loadStage("/view/Dashboard.fxml", "Dashboard", 1290, 720);

        // Close the login window
        Stage loginStage = (Stage) errorMessage.getScene().getWindow();
        loginStage.close();
    }
}