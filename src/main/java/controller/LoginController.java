package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import library.Library;
import library.Library.Admin;
import main.StageManager;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class LoginController extends Controller {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private Button loginButton;

    private boolean isAuthenticated = false;

    @FXML
    public void initialize() {
        super.initialize();

        loginButton.setOnAction(event -> handleLoginButtonAction());

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

        roleBox.setItems(FXCollections.observableArrayList("Admin", "User"));
        roleBox.setValue("Admin");
    }

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleBox.getValue();

        if ("Admin".equals(role)) {
            List<Admin> adminList = Library.admins;
            for (Admin admin : adminList) {
                if (admin.getName().equals(username) && admin.getPass().equals(password)) {
                    isAuthenticated = true;
                    break;
                }
            }
        } else if ("User".equals(role)) {
            authenticate(username, password);
        }

        if (isAuthenticated) {
            errorMessage.setText("Login successful!");
            errorMessage.setTextFill(javafx.scene.paint.Color.BLUE);
            openDashboard();
        } else {
            errorMessage.setText("Invalid username or password.");
            errorMessage.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    private void authenticate(String username, String password) {
        Thread dbThread = new Thread(() -> {
            try {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                isAuthenticated = resultSet.next();
            } catch (Exception e) {
                e.printStackTrace();
                isAuthenticated = false;
            }
        });
        dbThread.start();
        try {
            dbThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openDashboard() {
        StageManager.loadStage("/view/Dashboard.fxml", "Dashboard", 1290, 720);

        // Close the login window
        Stage loginStage = (Stage) errorMessage.getScene().getWindow();
        loginStage.close();
    }
}