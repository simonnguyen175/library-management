package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label dateLabel;

    @FXML
    private Label userCountLabel;

    @FXML
    private Label bookCountLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateLabel.setText("Date: " + LocalDate.now().toString());
        userCountLabel.setText("Number of Users: 100"); // Replace with actual user count
        bookCountLabel.setText("Number of Books: 500"); // Replace with actual book count
    }
}