package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void loadStage(String fxmlPath, String title, double width, double height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StageManager.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.show();

            root.setOnMouseClicked(event -> root.requestFocus());

            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}