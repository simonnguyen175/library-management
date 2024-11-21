package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import library.Book;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class BookDetailController {
    @FXML
    private Label bookTitleLabel;
    @FXML
    private Button backButton;
    @FXML
    private AnchorPane rootPane;



    public void setBookTitle(String title) {
        bookTitleLabel.setText(title);
    }



    @FXML
    private void initialize() {
        backButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Books.fxml"));
                AnchorPane booksPane = loader.load();
                rootPane.getChildren().setAll(booksPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}