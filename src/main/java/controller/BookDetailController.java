package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import library.Book;
import java.io.IOException;

public class BookDetailController {
    @FXML
    private Button backButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label labelTitle;
    @FXML
    private TextArea description;
    @FXML
    private ImageView bookImage;

    private Book book;



    @FXML
    private void initialize() {
        backButton.setOnAction(event -> {
            handleBackButtonAction();
        });
    }

    public void setBook(Book book) {
        this.book = book;
        labelTitle.setText(book.getTitle());
        Image image = new Image(book.getImageUrl());
        bookImage.setImage(image);
        new Thread(() -> {
            APIController apiController = APIController.getInstance();
            String des = apiController.getBookDescriptionFromAPI(book.getTitle());
            if (des != null) {
                description.setText(des);
            }
            else {
                description.setText("No description available");
            }
        }).start();
    }

    private void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Books.fxml"));
            AnchorPane booksPane = loader.load();
            rootPane.getChildren().setAll(booksPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}