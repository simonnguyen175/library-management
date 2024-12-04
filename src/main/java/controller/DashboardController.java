package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import library.Library;
import main.StageManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import static library.Library.role;

public class DashboardController extends Controller {
    @FXML
    private Button homeButton, booksButton, usersButton, borrowListButton, notiButton, logoutButton;

    @FXML
    private Label dateLabel, userCountLabel, bookCountLabel;

    @FXML
    private HBox menu;

    @FXML
    protected AnchorPane container;

    protected void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    protected void showPane(String path) {
        System.out.println("Loading pane: " + path); // Debug statement
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
            setNode(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        if (role == "admin") showPane("/view/Home.fxml");
        else showPane("/view/HomeUser.fxml");

        homeButton.setOnAction(actionEvent -> {
            try {
                container.getChildren().clear();
                if (role == "admin") showPane("/view/Home.fxml");
                else showPane("/view/HomeUser.fxml");
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

        if (role == "user") { // Assuming 1 represents the user role
            menu.getChildren().remove(usersButton);
        }

        usersButton.setOnAction(actionEvent -> {
            try {
                showPane("/view/Users.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        borrowListButton.setOnAction(actionEvent -> {
            try {
                showPane("/view/BorrowList.fxml");
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