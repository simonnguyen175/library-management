package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryManagement extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StageManager.loadStage("/view/Login.fxml", "Library Management", 640, 480);
    }

    public static void main(String[] args) {
        launch();
    }
}