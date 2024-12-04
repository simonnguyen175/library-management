package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.Library;
import library.User;

public class FixUserController extends Controller {
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button applyButton;
    @FXML
    private Label fullnameLabel;

    private User user;

    @FXML
    public void initialize() {
        applyButton.setOnAction(event -> applyChanges());

    }

    public void setUser(User user) {
        this.user = user;
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhone());
        fullnameLabel.setText(user.getFullname());
    }

    @FXML
    private void applyChanges() {
        user.setEmail(emailField.getText());
        user.setPhone(phoneField.getText());
        // Save changes to the database or perform other necessary actions
        Library myLib = Library.getInstance();
        myLib.fixUser(user);

        ((Stage) applyButton.getScene().getWindow()).close();
    }
}