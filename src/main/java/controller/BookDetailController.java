package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

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