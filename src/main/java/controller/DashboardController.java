package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import main.StageManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DashboardController {
    @FXML
    private Button homeButton, booksButton, usersButton, borrowListButton, notiButton, logoutButton;

    @FXML
    private AnchorPane container;

    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    private void showPane(String path) {
        System.out.println("Loading pane: " + path); // Debug statement
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
            setNode(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        showPane("/view/Home.fxml");
        homeButton.setOnAction(actionEvent -> {
            try {
                container.getChildren().clear();
                showPane("/view/Home.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        booksButton.setOnAction(actionEvent -> {
            try {
                showPane("/view/Books.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        usersButton.setOnAction(actionEvent -> {
            try {
                showPane("/view/Users.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        logoutButton.setOnAction(actionEvent -> {
            try {
                Stage currentStage = (Stage) logoutButton.getScene().getWindow();
                currentStage.close();
                StageManager.loadStage("/view/Login.fxml", "Library Management", 640, 480);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}