package controller;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import services.APIController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import library.Book;
import services.QRCodeGenerator;

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
    @FXML
    private Button borrowButton;
    @FXML
    private Button QRButton;
    @FXML
    private Label authorLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label isbnLabel;
    @FXML
    private Label pagesLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label languageLabel;

    private Book book;



    @FXML
    private void initialize() {
        backButton.setOnAction(event -> {
            handleBackButtonAction();
        });

        QRButton.setOnAction(event -> {
            handleQRButtonAction();
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

        authorLabel.setText("Tác giả: " + book.getAuthor());
        publisherLabel.setText("Nhà xuất bản: " + book.getPublisher());
        genreLabel.setText("Thể loại: " + book.getGenre());
        pagesLabel.setText("Số trang: " + book.getPages());
        yearLabel.setText("Năm xuất bản: " + book.getPublicationYear());
        languageLabel.setText("Ngôn ngữ: " + book.getLanguage());
        isbnLabel.setText("ISBN: " + book.getIsbn());
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

    private void handleQRButtonAction() {
        Popup popup = new Popup();
        ImageView qrCodeImage = new ImageView();

        QRCodeGenerator QR = QRCodeGenerator.getInstance();
        qrCodeImage.setImage(QR.generateQRCode(book.getIsbn()));

        // Create a DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.BLACK);
        qrCodeImage.setEffect(dropShadow);

        // Create a StackPane to add padding and set background color to white
        StackPane stackPane = new StackPane(qrCodeImage);
        stackPane.setStyle("-fx-padding: 10; -fx-background-color: darkgrey; -fx-border-color: darkgrey; -fx-border-width: 0;");

        popup.getContent().add(stackPane);
        popup.setAutoHide(true);
        popup.show(QRButton.getScene().getWindow());
    }
}